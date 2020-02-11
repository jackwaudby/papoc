## clear environment
remove(list=ls())

## load function
source(file = "./approximations/func_probabilistic.R")

## command line args
args = commandArgs(trailingOnly=TRUE)
eps = as.numeric(args[1])

## arrival rate 
lam = c(100,200,300,400,500,600,700,800,900,1000,2000,3000,4000,5000)

## time until corruption
U = rep(0,length(lam))

write(paste0(eps),file="./log/probabilistic.log",append=TRUE)
write("lam U",file="./log/probabilistic.log",append=TRUE)

# calculating d using eps and delta
calc_d <- function(eps,del){
     d=-log(eps)/del
     return(d)
}

# calc_d(eps=0.01,del=200)
# calc_d(eps=0.05,del=200)

print(paste0("eps = ",args[1]))
print(paste0("delta = ",calc_d(eps=eps,del=200)))
print("lam  time-taken  U")

for (i in 1:length(lam)) {
    start_time = Sys.time()
    U[i] = probabilistic(
        N=c(10^4,10^5,10^6,10^7,10^8),
        p=c(0.5,0.26,0.13,0.07,0.04),
        f=0.3,
        r=0.07,
        lam=lam[i],
        gam=0.1,
        eps=eps)
    end_time = Sys.time()
    duration = end_time - start_time
    print(paste0(lam[i]," ",duration," ",U[i]))
    write(paste0(lam[i]," ",duration),file="./log/probabilistic.log",append=TRUE)
}

results = cbind(lam,U)
write.csv(results,'./results/corruption/probabilistic.csv', row.names=FALSE)














