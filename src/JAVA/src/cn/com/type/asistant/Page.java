package cn.com.type.asistant;

public class Page {
	public int wholePage;
	private int currentPage;
	public int numPerPage;
	public int startNum;
	public int practicalCurrentPageNum;
	
	public Page(int currentPage, int numPerPage){
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.startNum = (this.currentPage-1)*this.numPerPage+1;
	}
	public void currentPlusOne(){
		this.currentPage++;
		this.startNum = (this.currentPage-1)*this.numPerPage+1;
	}
}
