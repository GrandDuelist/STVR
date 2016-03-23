source("iterate.lda.R")
source("iterate.cluster.R")
source("cluster.R")

iterate.result <- function(s=NULL,  # If calling this function a second time, this should be
                        # resulting object of the first
                        testDir=NULL,  # Name of the directory with the preprocessed test files
                        testNamesMap=NULL,  # testing map
                        truthName=NULL, # ground truth (fault matrix)
                        kDir="",
                        iter=200,   # LDA parameter number of iterations
                        alpha=0.1,  # LDA parameter alpha
                        beta=0.1,  #LDA parameter beta                        
                        distance,  # distance measure to use (passed to dist())
                        maximization, # max. algorithm to use (greedy or clustering)
                        rerunLDA=FALSE, # Should we rerun LDA, if it's already been run?
                        verbose=FALSE,
                        iT=30,
                        method="cluster_string",
                        productName)
{
  
  dataDir = "/home/zhihan/Workspace/STVR/data"
  #names <- c("15 for Tablets","16","16 for Tablets","17","17 Tablets","18","18 Tablets","19","19 Tablets","20","20 Tablets","21","21 Tablet","22","22 Tablet","23","23 Tablet","24","24 Tablet","25","25 Tablet","26","26 Tablet","27","27 Tablet","28","28 Tablet","29","29 Tablet","30 Tablet");
  productDir <- paste(dataDir,"/LDA_LAYERIZED_DATA/",productName,sep="");
  names <- list.files(productDir);
  
  #names <- c("80");
  apfds <-matrix(nrow=length(names),ncol=iT);
  for(m in 1:length(names)){
    testDir2=paste(dataDir,"/LDA_INPUT_DATA/",productName,"/",names[m],"/",sep="");
    clusterDir2=paste(dataDir,"/LDA_LAYERIZED_DATA/",productName,"/",names[m],"/",sep="");
    
    #testDir2=paste(testDir2,"/",sep="");
    truthName2=paste(dataDir,"/FAULT_MATRIX/",productName,"/",names[m],"/fault_matrix.txt",sep="");
    
    #truthName2=(truthName2,"/fault_matrix.txt");
    dirname = paste(dataDir,"/RESULT_REVERSE/",productName,"/",kDir,sep="");
    if(!file.exists(dirname)){
      dir.create(dirname,recursive = TRUE);
    }
    filename = paste(dataDir,"/RESULT_REVERSE/",productName,"/",kDir,"",names[m],".txt",sep="");
    sink(filename);
    
    for(n in 1:iT)
    {
      
     # print(clusterDir2)
      if(method=="string")
      {
        tcp <- tcp.string(testDir=testDir2,truthName=truthName2);
      }else if(method=="random")
      {
        tcp <- tcp.random(testDir=testDir2,truthName=truthName2);
      }else if(method=="cluster_random")
      {
        tcp <- random.cluster(testDir=testDir2,truthName=truthName2, testDirCluster = clusterDir2);
      }else if(method=="cluster_string")
      {
        tcp <- string.cluster(testDir=testDir2,truthName=truthName2, testDirCluster = clusterDir2);
      }else if(method=="lda_coverage"){
	      tcp <-tcp.lda(testDir=testDir2,truthName=truthName2,maximization="max",K=10);
      }else if(method=="lda_greedy"){
      	tcp <-tcp.lda(testDir=testDir2,truthName=truthName2,maximization="greedy",K=15);
      }
      #topic <- tcp.lda(testDir,K,truthName);
      apfds[m,n] <- tcp$apfd;
     # print(tcp$apfd)
      
      
    }
    
    apfds[m,]=sort(apfds[m,])
    print( apfds[m,]);
    
    print('medium:');
    
    medium_point=(iT+1)/2;
    if(is.integer(medium_point))
    {
      medium=apfds[m,medium_point];
    }else{
      medium=(apfds[m,(iT+2)/2]+apfds[m,iT/2])/2;
    }
    
    print(medium);
    sink();
  }
  apfds
}


iterate.result.all <- function(itNum=100,productName)
{
  
  
 #methods=c("cluster_string","cluster_random","string","random","lda_coverage","lda_greedy");
 #methods="lda_greedy"
 methods=c("cluster_string","cluster_random");
  #methods=c("cluster_random");
  for(i in 1:length(methods)){
    dirName = paste(methods[i],"/",sep="")
    iterate.result(iT=itNum,method=methods[i],kDir=dirName,productName = productName);
  }
}


iterate.result.all.products <- function(itNum = 100){
  TOP_LAYERIZED_DIR = "/home/zhihan/Workspace/STVR/data/LDA_LAYERIZED_DATA";
  TOP_INSTRUCTION_DIR =  "/home/zhihan/Workspace/STVR/data/LDA_INPUT_DATA";
  
  productNames <- list.files(TOP_LAYERIZED_DIR);
  
  for(i in 1:length(productNames)){
    
      iterate.result.all(productName = productNames[i]);
    
  }
  
}

