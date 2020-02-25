# PaPoC 20' #

## Paper ##

To build `.pdf` navigate to `paper/` and execute `make`.

## Corruption Approximations ##

Parameters are fixed as in EPEW 18'. Produce time to corruption saved in `results/`.
```
# run simulation with no isolation
./run-no-isolation.sh <lam> <gam>

# run delta protocol simulation
./run-delta-protocol-sim.sh <lam> <gam> <delta>
```

## Protocol Simulations ##

## Docker ##

```
# build image from Dockerfile
docker build -f Dockerfile -t time-to-corruption .
```
```
# run container, mount files, run files
docker run -v `pwd`/:/usr/local/src/scripts/ -d time-to-corruption ./run-no-isolation.sh <lam> <gam>

docker run -v `pwd`/:/usr/local/src/scripts/ -d time-to-corruption ./run-delta-protocol-sim.sh <lam> <gam> <delta>

```
