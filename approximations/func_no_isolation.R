no_isolation <- function(N,p,f,lam,r,gam){
  t=0
  n=matrix(rep(0,15),ncol=3,nrow=5)
  tr=matrix(rep(0,25),ncol=5,nrow=5)
  Nt=sum(N)
  n[,1]=(1-f)*N
  n[,2]=f*N
  q=1-exp(-0.5*lam*0.005*(p/N))
  while(sum(n)>Nt*(1-gam)){
    a=rep(0,5)
    for(i in 1:5){
      a[i]=(p[i]/N[i])*(n[i,1]+n[i,2]+0.5*n[i,3])
    }
    a=sum(a)
    b=a^2*r/(1-a+a*r)
    for(i in 1:5){
      tr[i,1]=((lam*p[i]*n[i,2])/N[i])*q[i]*b*b
      tr[i,2]=((lam*p[i]*n[i,3])/N[i])*(1-q[i])*b
      tr[i,3]=((lam*p[i]*n[i,3])/N[i])*(1-b)
      tr[i,4]=((lam*p[i]*n[i,2])/N[i])*(1-b)
      tr[i,5]=((lam*p[i]*n[i,1])/N[i])*(1-b)
    }
    tt=sum(tr)
    t = t + 1/tt
    i = sample(c(1, 2, 3, 4, 5), size = 1, replace = TRUE, prob = rowSums(tr)/tt)
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
  t = t/3600
  return(t)
}