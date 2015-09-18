require(gdata)

#mobile input
#mobile output
fileDir <- "/home/zhihan/Workspace/STVR/data/RESULT/Mobile Firefox No Tablets/mobile_result.xlsx"
outputDir <- "/home/zhihan/Workspace/STVR/data/PLOTS/Mobile Firefox No Tablet"

# tablets input
fileDir_tablets <- "/home/zhihan/Workspace/STVR/data/RESULT/Mobile Firefox Tablets Only/tablets_result.xlsx"
#tablet_output
outputDir_tablets <- "/home/zhihan/Workspace/STVR/data/PLOTS/Mobile Firefox Tablets Only"

fileDir_icst <- "/home/zhihan/Workspace/ICST/data/result/result_100times/result.xlsx"
outputDir_icst <- "/home/zhihan/Workspace/ICST/PLOTS"

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

	result <- read.xls(fileName,sheet=index);
	title = paste("APFD of v", version,sep="");
	boxplot(result$random,result$string,result$lda_greedy,result$cluster_random,result$cluster_string,
	                      names = c("Rnd","TxD","TpD","RDR","RDD"),main = title); 
}

plot.rsl.all <- function(fileDir,outputDir,start,end,nrow,ncol){
	allSheetNames = sheetNames(fileDir)
	#allSheetNames = sort(allSheetNames)
	print(allSheetNames)
	outputFileName <- paste(outputDir,"/","rsl",".jpeg",sep="")
	bitmap(file=outputFileName,type="jpeg",res=600,width=8000,height=6000,units="px")
	 par(mfrow=c(nrow,ncol),cex.lab=0.3)
	
	for(i in start:end){
		version = allSheetNames[i] 
		plot.rsl(fileDir,version,i)
	}
	dev.off()
}
