%function U = stepsFP(T,N,p,lam,r,h,f,gam)
T = 7;
N=[10^4,10^5,10^6,10^7,10^8,10^9,10^10];
p=[0.5,0.26,0.13,0.05,0.03,0.02,0.01];
lam=1000;
r=0.07;
h=3600;
f=0.3;
gam=0.1;

%Find corruption time U and vectors (ni0,ni1,ni2);
%use Fixed-Point iterations at each step to find beta
%T=no. of types; N=vector of edge numbers; p=distribution of accesses;
%lam=arr rate; r=geometric distr. of reads r(1-r)^(i-2);  
%h= time increment; f=fract. distr. edges; gam=fract. corrupted
n0 = zeros(T,3);                   %Last ni0, ni1, ni2
n1 = zeros(T,3);                   %Next ni0, ni1, ni2
nn = sum(N);                       %total no. of edges
q = 1 - exp(-0.5*lam*0.005*p./N)'; %conflict probability vector
%q = zeros(7,1);
z = (lam*h*p./N)';                 %column of common factors
n0(:,[1,2]) = [(1-f)*N',f*N'];     %initial nij
U = 0;
b = 1;                             %initial estimate of beta
n1(:,1) = n0(:,1)-(1-b)*z.*n0(:,1);                            %next ni0
n1(:,2) = n0(:,2)+z.*(b*(1-q).*n0(:,3)-(b^2*q+1-b).*n0(:,2));  %next ni1
n1(:,3) = n0(:,3)+z.*(b^2*q.*n0(:,2)-(1-q*b).*n0(:,3));        %next ni2
n = (n0 + n1)/2;                        %averages
while nn-sum(sum(n1))<gam*nn
    a = (p./N)*(n(:,1)+n(:,2)+0.5*n(:,3));  %alpha
%    bA = a^2*r/(1-a*(1-r));                 %ave. beta
    bA = a*r/(1-a*(1-r));                   %ave. beta
    while abs(b-bA) > 0.000001
        b = bA;
        x = max(1-(1-b)*z,0);                     %non-negative multiplier
        n1(:,1) = n0(:,1).*x;                        %next ni0
        x = max(1-(b^2*q+1-b).*z,0);              %non-negative multiplier
        n1(:,2) = n0(:,2).*x + b*(1-q).*z.*n0(:,3);  %next ni1
        x = max(1-(1-q*b).*z,0);                  %non-negative multiplier
        n1(:,3) = n0(:,3).*x + b^2*q.*z.*n0(:,2);    %next ni2
        n = (n0 + n1)/2;                        %averages
        a = (p./N)*(n(:,1)+n(:,2)+0.5*n(:,3));  %alpha
%        bA = a^2*r/(1-a*(1-r));                 %ave. beta
        bA = a*r/(1-a*(1-r));                   %ave. beta
    end
    a = (p./N)*(n1(:,1)+n1(:,2)+0.5*n1(:,3));   %next alpha
%    b = a^2*r/(1-a*(1-r));                      %next beta
    b = a*r/(1-a*(1-r));                        %next beta
    U = U + h;                                  %increment U
    n0 = n1;
    n1(:,1) = n0(:,1)-(1-b)*z.*n0(:,1);                            %ni0
    n1(:,2) = n0(:,2)+z.*(b*(1-q).*n0(:,3)-(b^2*q+1-b).*n0(:,2));  %ni1
    n1(:,3) = n0(:,3)+z.*(b^2*q.*n0(:,2)-(1-q*b).*n0(:,3));        %ni2
    n = (n0 + n1)/2;                        %averages
end
%U = U/(60*60);                             %time in hours
U = U/(24*60*60);                          %time in days
%end


