## Bitonic Sort

A [Bitonic Sort](https://www.geeksforgeeks.org/bitonic-sort/) is a sorting algorithm that is easily parallelizable.

Compile with `mpic++ bitonic-sort.cpp`

Run with `mpirun -np <num-processes> --oversubscribe a.out`

### Dependencies

* [MPI](https://www.open-mpi.org/)
  * `sudo apt-get install libopenmpi-dev`
* Must be compiled with C++11
  * This will likely require some changes to the MPI configuration files
    * To run with commands above, ensure C++11 is specified in mpic++-wrapper-data.txt
