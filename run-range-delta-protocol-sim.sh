#!/bin/bash

for i in 1000 2000 3000 4000 5000 6000 7000 8000 9000 10000;
do docker run -v `pwd`/:/usr/local/src/scripts/ -d delta-protocol ./run-range-delta-protocol-sim.sh $i 0.1 $1;
done
