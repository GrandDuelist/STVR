package cn.com.model.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihan
 *
 */
public class LayerizedStepPerformBean {
	List<PreviousClusterBean> orderedCluster = new ArrayList<PreviousClusterBean>();
	String versionNames;
	
	public List<PreviousClusterBean> getOrderedCluster() {
		return orderedCluster;
	}
	public void setOrderedCluster(List<PreviousClusterBean> orderedCluster) {
		this.orderedCluster = orderedCluster;
	}
	public String getVersionNames() {
		return versionNames;
	}
	public void setVersionNames(String versionNames) {
		this.versionNames = versionNames;
	}

}




