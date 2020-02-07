## comparison plot
comparison <- function(save=F,df){
  if(save==T){
    pdf("../images/comparison.pdf")
  }
  p=ggplot(data=df, aes(x=lam,y=u,linetype=type,color=type)) +
    geom_line() +
    scale_linetype_discrete(name="Type: ",labels=unname(TeX(c('$\\epsilon = 100%$',"$\\epsilon$ = 5%","$\\epsilon$ = 1%")))) +
    scale_color_discrete(name="Type: ",labels=unname(TeX(c('$\\epsilon = 100%$',"$\\epsilon$ = 5%","$\\epsilon$ = 1%")))) +
    scale_x_continuous(name=unname(TeX(c("Read-Write Transactions per second (10% TPS)"))),
                       breaks=seq(100,1500,200)) +
    scale_y_continuous(name=unname(TeX(c("Time (in hours) for 10% DB Corruption"))),
                       breaks=seq(0,3000,500)) +
    theme(legend.text.align = 0)
  p
  if(save==T){
    dev.off()
  }
  return(p)
}

comparison(save=T)