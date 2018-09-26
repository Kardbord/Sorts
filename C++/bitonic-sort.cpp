#include <mpi.h>
#include <iostream>
#include <cstdlib>
#include <unistd.h>
#include <stdexcept>
#include <cmath>
#include <vector>
#include <algorithm>
#include <string>

#define MCW MPI_COMM_WORLD

using _uint_ = unsigned long int;

// Prints helpful debug output
// Waits for input before continuing
void debugPrint(int data) {
    int size, rank;
    MPI_Comm_size(MCW, &size);
    MPI_Comm_rank(MCW, &rank);


    // Send data back to master process
    if (rank > 0) {
        MPI_Send(&data, 1, MPI_INT, 0, 0, MCW);
    }

    if (rank == 0) {
        int recvData;
        std::cout << data << ", ";
        for (_uint_ i = 1; i < size; ++i) {
            MPI_Recv(&recvData, 1, MPI_INT, i, 0, MCW, MPI_STATUS_IGNORE);
            std::cout << recvData << ", ";
        }
        std::cout << std::endl;
        std::string in;
        std::cin >> in;
    }
    
    MPI_Barrier(MCW);

}

// Sends @sendData from a process to the process whose
// rank differs only in the @i-th bit, and receives data from
// that same process.
// If the @i'th bit being considered is a one in the rank 
// of the calling process, it keeps the larger of the two values.
// If the @i'th bit being considered is a zero in the rank
// of the calling process, it keeps the smaller of the two values.
//
// param i        : the bit to be considered when comparing ranks
// param sendData : the data to be sent from this process to its @i-th bit compliment
// param steps    : the total number of steps in this round of the bitonic sort
// return         : the data received from the other process or the original data
//                  depending on the if this process and its compliment are sorting 
//                  ascending or descending in this portion of the bitonic sort
int bitonic_cube(int i, int const & sendData, int const & steps) {
    --i;

    if (i < 0) {
        throw std::invalid_argument(
            "Error in arguments to bitonic_cube() : parameter i must be > 0"
        );
    }

    int recvData;
    int rank;
    MPI_Comm_rank(MCW, &rank);

    int mask = 1 << i;

    int dest = rank ^ mask;

    MPI_Send(&sendData, 1, MPI_INT, dest, 0, MCW);
    MPI_Recv(&recvData, 1, MPI_INT, dest, 0, MCW, MPI_STATUS_IGNORE);

    int rank_bit = (rank >> i) & 1;
    int dest_bit = (dest >> i) & 1;
    int i_bit = (rank >> steps) & 1;
    if (i_bit == 0) {
        // Sort ascending
        if (rank_bit == 1 && dest_bit == 0) return ((recvData >= sendData) ? recvData : sendData);
        if (rank_bit == 0 && dest_bit == 1) return ((recvData <= sendData) ? recvData : sendData);
        throw "something has gone horribly wrong in bitonic_cube() : ERR 1";
    } else if (i_bit == 1) {
        // Sort descending
        if (rank_bit == 1 && dest_bit == 0) return ((recvData <= sendData) ? recvData : sendData);
        if (rank_bit == 0 && dest_bit == 1) return ((recvData >= sendData) ? recvData : sendData);
        throw "something has gone horribly wrong in bitonic_cube() : ERR 2";
    } else {
        throw "something has gone horribly wrong in bitonic_cube() : ERR 3";
    }

}

// This program only works with power of 2 processors
//
// param size : the number of processors in the MCW
// return     : true if @size is a power of two, false otherwise
bool validNumProcesses(unsigned const & size) {
    return ((size - 1) & size) == 0 && size != 1;
}

// Bitonic Sorting Algorithm
// See https://www.geeksforgeeks.org/bitonic-sort/
int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);

    int rank;
    int size;
    MPI_Comm_rank(MCW, &rank);
    MPI_Comm_size(MCW, &size);

    if (rank == 0) {
        if (!validNumProcesses(size)) {
            std::cerr << "This program only works with 2^j, j > 0 processes" << std::endl;
            std::cout << "This program only works with 2^j, j > 0 processes" << std::endl
                      << "Exiting..." << std::endl;
            return EXIT_FAILURE;
        }
    }

    // Assign each process its data
    srand(static_cast<unsigned long>(rank));
    int data = rand() % (rank + 100);
    
    // Send data to master process print list
    if (rank > 0) {
        MPI_Send(&data, 1, MPI_INT, 0, 0, MCW);
    }

    // Print list and expected output
    std::string expectedOutput = "";
    std::vector<int> list;
    if (rank == 0) {

        // Print list 
        int recvData;
        list.push_back(data);
        std::cout << "List:" << std::endl;
        std::cout << data << ", ";
        for (_uint_ i = 1; i < size; ++i) {
            MPI_Recv(&recvData, 1, MPI_INT, i, 0, MCW, MPI_STATUS_IGNORE);
            list.push_back(recvData);
            std::cout << recvData << ", ";
        }
        std::cout << std::endl;

        // Print expected output
        std::sort(list.begin(), list.end());
        for (auto && e : list) {
            expectedOutput += std::to_string(e) + ", ";
        }
        std::cout << "\nExpected Output:\n" << expectedOutput << std::endl;
        list.clear();
    }


    int i_bit = static_cast<int>(std::log(size) / std::log(2));

    // Perform the bitonic sort
    for (int i = 1; i <= i_bit; ++i) {
        for (int j = i; j > 0; --j) {
            data = bitonic_cube(j, data, i);
            // debugPrint(data); // Uncomment this line to walk through the bitonic sort
        }
    }

    // Send data back to master process
    if (rank > 0) {
        MPI_Send(&data, 1, MPI_INT, 0, 0, MCW);
    }

    // Print output
    if (rank == 0) {

        // Gather sorted data from other processes
        list.push_back(data);
        for (_uint_ i = 1; i < size; ++i) {
            MPI_Recv(&data, 1, MPI_INT, i, 0, MCW, MPI_STATUS_IGNORE);
            list.push_back(data);
        }


        // Print bitonic sort output
        std::string bitonicOutput = "";
        for (auto && e : list) {
            bitonicOutput += std::to_string(e) + ", ";
        }
        std::cout << "\nBitonic Sort Output:\n" <<  bitonicOutput << std::endl;

        std::cout << "\nOutput was as expected: " 
                  << ((bitonicOutput == expectedOutput) ? "yes" : "no")
                  << std::endl << std::endl;
    }

    MPI_Finalize();

    return EXIT_SUCCESS;
}
