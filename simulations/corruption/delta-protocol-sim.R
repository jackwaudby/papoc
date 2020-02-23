## command line args
args = commandArgs(trailingOnly=TRUE)
lam = as.numeric(args[1])
gam = as.numeric(args[2])
eps = as.numeric(args[3])

setwd("./simulations/corruption")

## load function
source(file = "./func_probabilistic.R")
source(file= "./calculate_delta.R")

## time until corruption
U = 0

## parameters
N=c(10^4,10^5,10^6,10^7,10^8)
p=c(0.5,0.26,0.13,0.07,0.04)
f=0.3
r=0.07
gam=gam
del=200

start_time = Sys.time()
U = probabilistic(
    N=N,
    p=p,
    f=f,
    r=r,
    lam=lam,
    gam=gam,
    eps=eps,
    del=200)

end_time = Sys.time()
duration = (end_time - start_time)/1000/60


results = cbind(lam,U,duration)
filename = paste0('../../results/probabilistic_',lam,'_',gsub("\\.","\\_",eps),'.csv')
write.csv(results,filename,row.names=FALSE)
