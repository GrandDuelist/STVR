require(gdata)

#mobile input
#mobile output
fileDir <- "/home/zhihan/workspace/STVR/data/RESULT/Mobile Firefox No Tablets/mobile_result.xlsx"
outputDir <- "/home/zhihan/workspace/STVR/data/PLOTS/Mobile Firefox No Tablet"

# tablets input
fileDir_tablets <- "/home/zhihan/workspace/STVR/data/RESULT/Mobile Firefox Tablets Only/tablets_result.xlsx"
#tablet_output
outputDir_tablets <- "/home/zhihan/workspace/STVR/data/PLOTS/Mobile Firefox Tablets Only"

fileDir_icst <- "/home/zhihan/workspace/ICST/data/result/result_100times/icst_result.xlsx"
outputDir_icst <- "/home/zhihan/workspace/ICST/PLOTS"

plot.mobile.auto <- function(start,end,nrow,ncol){
	plot.rsl.all(fileDir,outputDir,start,end,nrow,ncol)
}

## start and end is the version index, 1 represents version 16 and 14 represents 29
## nrow and ncol is the layout row number and column number
plot.tablets.auto <- function(start,end,nrow,ncol){
	plot.rsl.all(fileDir_tablets,outputDir_tablets,start,end,nrow,ncol);
}

plot.icst.auto<- function(start,end,nrow,ncol){
	plot.rsl.all(fileDir_icst,outputDir_icst,start,end,nrow,ncol)
}




plot.rsl <- function(fileName,version,index){
	show_name = change.name(version);
	result <- read.xls(fileName,sheet=index);
	title = paste("APFD of v", show_name,sep="");
	boxplot(result$cluster_string,result$reverse_rdd,
	                      names = c("HBD","NHBD"),main = title,ylim=c(40,100)); 
}

plot.rsl.all <- function(fileDir,outputDir,start,end,nrow,ncol){
	allSheetNames = sheetNames(fileDir)
	#allSheetNames = sort(allSheetNames)
	print(allSheetNames)
	outputFileName <- paste(outputDir,"/","rsl",".tiff",sep="")
	#bitmap(file=outputFileName,type="tiff12nc",res=800,width=8000,height=6000,units="px")
	bitmap(file=outputFileName,type="tifflzw",res=800,width=8000,height=6000,units="px")
	par(mfrow=c(nrow,ncol),cex.lab=0.3)
	
	for(i in start:end){
		version = allSheetNames[i] 
		plot.rsl(fileDir,version,i)
	}
	dev.off()
}


change.name <- function(init_version_name){
	if(grepl("Firefox",init_version_name)){
		p_length=nchar("Firefox");
		v_length=nchar(init_version_name);
		start = grep("Firefox",init_version_name);
		v_number = substr(init_version_name,start+p_length+1,v_length);
		result = paste(v_number," ","Desktop",sep="")
	}else if(grepl("Tablet",init_version_name)){
		if(grepl("for Tablets",init_version_name)){
			result = gsub("for Tablets","Tablet",init_version_name)
		}else if(grepl("Tablets",init_version_name)){
			result = gsub("Tablets","Tablet",init_version_name)
		}else{
			result =init_version_name
		}
	}else{
		result = paste(init_version_name,"Mobile");
	}
	return(result);	
}