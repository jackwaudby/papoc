# PaPoC 20' #

## Paper ##

To build `.pdf` navigate to `paper/` and execute `make`.

## Corruption Approximations ##

Parameters are fixed as in EPEW 18'. Produce time to corruption saved in `results/`.
```
# run simulation with no isolation
./run-no-isolation.sh <lam> <gam>

# run delta protocol simulation
./run-delta-protocol-sim.sh <lam> <gam> <eps>
```

## Protocol Simulations ##

## Docker ##

```
# build image from Dockerfile
docker build -f Dockerfile.no -t time-to-corruption .
docker build -f Dockerfile.delta -t delta-protocol .

```
```
# run container, mount files, run files
docker run -v `pwd`/:/usr/local/src/scripts/ -d time-to-corruption ./run-no-isolation.sh <lam> <gam>

docker run -v `pwd`/:/usr/local/src/scripts/ -d delta-protocol ./run-delta-protocol-sim.sh <lam> <gam> <eps>

```
