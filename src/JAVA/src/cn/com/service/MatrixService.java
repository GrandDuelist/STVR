package cn.com.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.factory.DAOFactory;
import cn.com.io.BasicFileController;
import cn.com.io.PersonizedFileIO;
import cn.com.model.beans.CaseStepBugsBean;
import cn.com.model.beans.Product;
import cn.com.model.beans.StepResultProductBean;
import cn.com.sql.dao.CaseStepBugsDAO;
import cn.com.type.asistant.DataMapping;
import cn.com.type.asistant.Page;

public class MatrixService {
	Connection conn;
	List<Product> productsList;
	List<CaseStepBugsBean> caseBugs;
	HashMap<String, Product> productsMap;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public MatrixService() {

	}

	public void generateFaultMatrixAllProduct() {
		this.envelopProducts();
		this.generateFaultMatrixMap();
		this.outputFaultMatrix();
	}

	/**
	 * 将productsMap 输出为fault matrix
	 */
	public void outputFaultMatrix() {
		Iterator<Entry<String, Product>> it = this.productsMap.entrySet()
				.iterator();
		System.out.println(this.productsMap.entrySet().size());
		while (it.hasNext()) { // 每个产品
			Product currentProduct = (Product) it.next().getValue();
			String dir = DataMapping.TOP_FAULT_MATRIX_DIR
					+ DataMapping.PATH_SEPERATOR
					+ currentProduct.getProductName()
					+ DataMapping.PATH_SEPERATOR
					+ currentProduct.getProductVersion();
			BasicFileController.createDirectory(dir);
			String filePath = dir + DataMapping.PATH_SEPERATOR
					+ DataMapping.FAULT_MATRIX;
			PersonizedFileIO io = new PersonizedFileIO(filePath);
			boolean hasMatrix = currentProduct.bugs != null
					&& currentProduct.bugs.size() > 0;
			if (hasMatrix) {
				for (int i = 0; i < currentProduct.testCases.size(); i++) {
					CaseStepBugsBean currentTestCase = currentProduct.testCases
							.get(i);
					Iterator<Entry<String, Boolean>> it2 = currentTestCase.faultMatrixMap
							.entrySet().iterator();
					String line = "";
					// line += currentTestCase.testcase.getResultID()+" ";
					while (it2.hasNext()) {
						line += (it2.next().getValue() ? 1 : 0) + " ";

					}
					io.appendFile(line + "\n");
					io.close();
				}
			}
		}

	}

	/**
	 * 找出一个版本出现的所有bugs
	 */
	public void generateFaultMatrixMap() {
		Iterator<Entry<String, Product>> it = this.productsMap.entrySet()
				.iterator();

		while (it.hasNext()) { // 每个产品
			Product currentProduct = (Product) it.next().getValue();
			// 初始化每个版本的fault matrix
			List<String> bugs = currentProduct.bugs;
			// 对所有的测试用例
			for (int i = 0; i < currentProduct.testCases.size(); i++) {
				CaseStepBugsBean currentTestCase = currentProduct.testCases
						.get(i);
				currentTestCase.faultMatrixMap = new HashMap<String, Boolean>();

				for (int j = 0; j < bugs.size(); j++) { // 所有的bug
					currentTestCase.faultMatrixMap.put(bugs.get(j), false);
				}

				for (int j = 0; j < currentTestCase.bugurls.size(); j++) {
					currentTestCase.faultMatrixMap.put(
							currentTestCase.bugurls.get(j), true);
				}
			}
		}
	}

	/**
	 * 将caseBugs转换为productsMap，但是没有每个产品所有的bugs
	 */
	public void envelopProducts() {
		Page page = new Page(1, DataMapping.MAX_NUM_TEST_CASE);
		CaseStepBugsDAO casebugDAO = DAOFactory.getCaseStepBugsDAO();
		casebugDAO.setConnection(this.conn);
		this.caseBugs = casebugDAO.selectByPage(page);
		productsMap = new HashMap<String, Product>();

		for (int i = 0; i < this.caseBugs.size(); i++) {
			CaseStepBugsBean casebug = this.caseBugs.get(i);
			String mapKey = casebug.testcase.getProduct() + "_"
					+ casebug.testcase.getVersion();
			Product currentProduct = productsMap.get(mapKey);
			if (currentProduct == null || !productsMap.containsKey(mapKey)) {
				currentProduct = new Product();
				currentProduct.bugs = new ArrayList<String>();
				currentProduct.testCases = new ArrayList<CaseStepBugsBean>();
				currentProduct.setProductName(casebug.testcase.getProduct());
				currentProduct.setProductVersion(casebug.testcase.getVersion());
				this.productsMap.put(mapKey, currentProduct);
			}
			for(int j=0;j<casebug.bugurls.size();j++){
				if(!currentProduct.bugs.contains(casebug.bugurls.get(j))){
					currentProduct.bugs.add(casebug.bugurls.get(j));
				}	
			}
			
			currentProduct.testCases.add(casebug);
		}

	}

}
