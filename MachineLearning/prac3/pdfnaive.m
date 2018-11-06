function pdf = pdfnaive(dat,flag,ijk,h,ql0)
% Calculate the probability density function for selected columns of a data array
%   using the naive estimator [ center a bin of width 2h on every unique value] and count occurrences in this bin
%   This routine is too slow to use for a large data set in which every value in unique
%   Data like Kp and Dst are large datasets but have only a few unique values
%   The parameter h is adjusted to corespond to the quantization level of the data to assure proper bin width
%       pdf = pdfnaive(dat,flag,ijk,h,ql)
%   Created January 13, 2001 in Bern, Switzerland, Mods: Jan 18, 2001
%   dat     =       The data array {dn, x1, x2, ...}
%   flag    =       The flag used in data array
%   ijk     =       An array of indices specifying which cols to process (just col 2 now)
%   h       =       The half width of bins for pdf
%                   h will be adjusted by rounding to the nearest integer multiple of the quantization level
%                   Then the full width of the bin is 2*h+ql (the ql comes from the width of the center bin)
%   ql      =       The quantization level of the data
%                   (e.g. Dst is rounded to quantization level of 1 nT, Kp to 1/3 unit)
%   pdf     =       The array of bin centers and the prob density

[md,nd]     = size(dat);
[mx,nx]     = size(ijk);


% PROCESS EACH SELECTED COMPONENT
i   = 1;
%for i = 1:length(ijk)
    is  = find(dat(:,ijk(i))~=flag);            %Find the unflagged data in sel col
    x   = dat(is,ijk(i));                       %The good data in selected column
    ql  = finquant(x,ql0)                       %Find the quantization level of the data
    h   = ql*round(h/ql);                       %Adjust h to be an integer multiple of quantization level
    nx  = length(x);                            %The total number of samples in pdf
    fac = 1/(nx*(2*h+ql));                      %Factor to convert histogram count to prob den
    [y,i,j] = unique(x);                        %Find the unique values
    nu  = length(y);                            %Number of unique value
    pdf = repmat(0,nu,2);                       %Array to hold the values and prob den
    % LOOP FOR EVERY UNIQUE VALUE (Won't work for large data sets of high resolution measurements)
    % Below I should have used the array of unique values an # of occ of unique values
    for j = 1:nu                                %Loop for every unique value
        pdf(j,1) = y(j);                        %The current unique value
        is  = find(x>=y(j)-h & x<=y(j)+h);      %Find all points that fit in a 2h bin centered on current unique value
        pdf(j,2) = length(is)*fac;              %Number of occurrences of good x in bin centered on this value
    end
%end 
    return
        
        