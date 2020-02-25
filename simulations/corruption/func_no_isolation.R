# N - vector of edges
# p - access probs
# f - fraction dist
# lam - arrival rate
# r - geom
# gam - corrupted prop

no_isolation <- function(N,p,f,lam,r,gam,del){

  t=0 # init. time
  n=matrix(0,ncol=3,nrow=length(N)) # db state
  tr=matrix(0,ncol=5,nrow=length(N)) # transition matrix
  Nt=sum(N) # total number of edges
  n[,1]=(1-f)*N # init. local
  n[,2]=f*N # init. dist

  q=(lam*del / (2*N + lam*del)) # conflict prob

  while(sum(n)>Nt*(1-gam)){
    a=rep(0,length(N))
    for(i in 1:length(N)){
      a[i]=(p[i]/N[i])*(n[i,1]+n[i,2]+0.5*n[i,3])
    }
    a=sum(a) # read prob
    b=a^2*r/(1-a+a*r) # all reads can prob
    for(i in 1:length(N)){
      tr[i,1]=((lam*p[i]*n[i,2])/N[i])*q[i]*b*b
      tr[i,2]=((lam*p[i]*n[i,3])/N[i])*(1-q[i])*b
      tr[i,3]=((lam*p[i]*n[i,3])/N[i])*(1-b)
      tr[i,4]=((lam*p[i]*n[i,2])/N[i])*(1-b)
      tr[i,5]=((lam*p[i]*n[i,1])/N[i])*(1-b)
    }
    tt=sum(tr)
    t = t + 1/tt
    i = sample(seq(1,length(N)), size = 1, replace = TRUE, prob = rowSums(tr)/tt)
    j = sample(c(1, 2, 3, 4, 5), size = 1, replace = TRUE, prob = tr[i,]/sum(tr[i,]))
    if(j==1){
      n[i,2] = n[i,2] - 1
      n[i,3] = n[i,3] + 1
    } else if(j==2) {
      n[i,2] = n[i,2] + 1
      n[i,3] = n[i,3] - 1
    } else if(j==3) {
      n[i,3] = n[i,3] - 1
    } else if(j==4) {
      n[i,2] = n[i,2] - 1
    } else {
      n[i,1] = n[i,1] - 1
    }
  }
  t = t/(3600*24)
  return(t)
}
