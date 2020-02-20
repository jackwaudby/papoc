args = commandArgs(trailingOnly=TRUE)
lam = as.numeric(args[1])

## load function
## source(file = "./simulations/corruption/func_no_isolation.R")
source(file = "./func_no_isolation.R")

## arrival rate
## lam = c(100,200,300,400,500,600,700,800,900,1000,2000,3000,4000,5000)

## time until corruption
U = rep(0,length(lam))

## parameters
N=c(10^4,10^5,10^6,10^7,10^8,10^9,10^10)
p=c(0.5,0.25,0.13,0.06,0.03,0.02,0.01)
f=0.3
r=0.07
gam=0.1
del=200

print("lam  time taken  U")
#write("lam U",file="./log/no_isolation.log",append=TRUE)

for (i in 1:length(lam)) {
  start_time = Sys.time()
  U[i] = no_isolation(
    N=N,
    p=p,
    f=f,
    r=r,
    lam=lam[i],
    gam=gam,
    del=del)
  end_time = Sys.time()
  duration = end_time - start_time
  print(paste0(lam[i]," ",duration," ",U[i]))
  #write(paste0(lam[i]," ",duration),file="./log/no_isolation.log",append=TRUE)
}

results = cbind(lam,U)
## write.csv(results,'./results/no_isolation.csv', row.names=FALSE)
filename=paste0("./no_isolation_",lam,".csv")
write.csv(results,'./no_isolation.csv', row.names=FALSE)

# ArXiv paper parameters
# N=c(10^4,10^5,10^6,10^7,10^8,10^9,10^10)
# p=c(0.5,0.25,0.13,0.06,0.03,0.02,0.01)
# f=0.3
# lam=10000
# r=0.07
# gam=0.1
# del=200
