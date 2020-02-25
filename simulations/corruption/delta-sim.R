## command line args
args = commandArgs(trailingOnly=TRUE)
lam = as.numeric(args[1])
gam = as.numeric(args[2])
D = as.numeric(args[3])

setwd("./simulations/corruption")

## load function
source(file = "./func_delta.R")

## time until corruption
U = 0

## parameters
N=c(10^4,10^5,10^6,10^7,10^8,10^9,10^10)
p=c(0.5,0.25,0.13,0.06,0.03,0.02,0.01)
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
    D=D,
    del=del)

end_time = Sys.time()
duration = (end_time - start_time)/60


results = cbind(lam,U,duration)
filename = paste0('../../results/delta_',lam,'_',gsub("\\.","\\_",D),'.csv')
write.csv(results,filename,row.names=FALSE)
