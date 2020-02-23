#!/bin/bash

for i in 100 250 500 750 1000 2000 3000 4000 5000;
do docker run -v `pwd`/:/usr/local/src/scripts/ -d delta-protocol ./run-delta-protocol-sim.sh $i 0.1 $1;
done
