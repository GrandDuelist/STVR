package cn.com.type.asistant;

public class Page {
	public int wholePage;
	public int currentPage;
	public int numPerPage;
	public int startNum;
	
	public Page(int currentPage, int numPerPage){
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.startNum = (this.currentPage-1)*this.numPerPage+1;
	}
}
