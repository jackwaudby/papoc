#!/bin/bash

docker build -f Dockerfile -t abort-sim .

for i in 1 5
do
for j in 1000 2000 3000 4000 5000 6000 7000 8000 9000 10000
do docker run -v `pwd`/:/usr/local/src/scripts -d abort-sim java -cp "./target/uber-probabilistic-1.0-SNAPSHOT.jar" ProbabilisticProtocolSimulation $i $j
done
done
