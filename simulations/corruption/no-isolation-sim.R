args = commandArgs(trailingOnly=TRUE)
lam = as.numeric(args[1])
gam = as.numeric(args[2])

setwd("./simulations/corruption")

## load function
source(file = "./func_no_isolation.R")

## time until corruption
U = 0

## parameters

N=c(10^4,10^5,10^6,10^7,10^8,10^9)
#p=c(0.5,0.26,0.13,0.07,0.04)
p=c(0.5,0.25,0.12,0.07,0.04,0.02)
f=0.3
r=0.07
gam=gam
del=200


start_time = Sys.time()
U = no_isolation(
    N=N,
    p=p,
    f=f,
    r=r,
    lam=lam,
    gam=gam,
    del=del)
  end_time = Sys.time()
  duration = (end_time - start_time)/1000/60

results = cbind(lam,U,duration)
filename=paste0("../../results/corruption/no_isolation_",lam,".csv")
write.csv(results,filename, row.names=FALSE)
