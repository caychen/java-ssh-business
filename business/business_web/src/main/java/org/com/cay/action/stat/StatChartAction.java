package org.com.cay.action.stat;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.com.cay.action.BaseAction;
import org.com.cay.service.stat.IStatChartService;
import org.com.cay.utils.file.FileUtil;

public class StatChartAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IStatChartService statChartService;
	
	public void setStatChartService(IStatChartService statChartService) {
		this.statChartService = statChartService;
	}

	/*
	 * 厂家销售排行（旧版amcharts）
	 */
	/*public String factorysale() throws Exception{
		String sql = "select factory_name, sum(amount) samount "
				+ "from contract_product_c "
				+ "group by factory_name "
				+ "order by samount desc";
		
		//执行sql语句
		List<String> list = statChartService.executeSQL(sql);
		
		//可以先将最后几个小数据合并为其他项？？？？
		
		//组织符合要求的xml数据
		String content = generatePieData(list);
		
		//将拼接的字符串写入到data.xml中
		writeXML("stat/chart/factorysale/data.xml", content);
		
		return "factorysale";
	}*/
	
	/*
	 * 厂家销售排行（新版amcharts）
	 */
	public String factorysale() throws Exception{
		String sql = "select factory_name, sum(amount) samount "
				+ "from contract_product_c "
				+ "group by factory_name "
				+ "order by samount desc";
		
		//执行sql语句
		List<String> list = statChartService.executeSQL(sql);
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		/*
		 * {
                "country": "USA",
                "visits": 4025,
                "color": "#FF0F00"
            }
		 */
		String[] colors = {"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01"};
		int j = 0;
		for(int i = 0;i < list.size(); ++i){
			sb.append("{").append("\"factory\":\"").append(list.get(i)).append("\",")
						.append("\"amount\":\"").append(list.get((++i))).append("\",")
						.append("\"color\":\"").append(colors[j++]).append("\"}")
						.append(",");
			j = j >= colors.length ? 0 : j;
		}
		
		sb.delete(sb.length() - 1, sb.length());
		
		sb.append("]");
		
		super.pushObject("result", sb.toString());
		
		return "factorysale-new";
	}

	//生成饼图数据源
	
	private String generatePieData(List<String> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<pie>");
		
		for(int i = 0; i < list.size(); i += 2){
			sb.append("<slice title=\"" + list.get(i) + "\" pull_out=\"true\">" + list.get(i + 1) + "</slice>");
		}
		
		sb.append("</pie>");
		return sb.toString();
	}

	private void writeXML(String filename, String content) throws FileNotFoundException {
		String sPath = ServletActionContext.getServletContext().getRealPath("/");
		FileUtil.createTxt(sPath, filename, content, "UTF-8");
	}
	
	/*
	 * 在线人数的统计
	 */
	public String onlineinfo() throws Exception{
		//oracle
//		String sql = "select a.a1, nvl(b.c, 0) from "
//				+ "(select * from online_info_t) a "
//				+ "left join "
//				+ "(select to_char(login_time, 'HH24') a1, count(*) c from login_log_p group by to_char(login_time, 'HH24') order by a1) b "
//				+ "on a.a1=b.a1 "
//				+ "order by a.a1";
		
		//mysql
		String sql = "select a.a1,IFNULL(b.c,0) from "
				+ "(select * from online_info_t) a "
				+ "left JOIN "
				+ "(select DATE_FORMAT(login_time,'%H') a1, count(*) c from login_log_p group by DATE_FORMAT(login_time,'%H') order by a1) b "
				+ "on a.a1 = b.a1 "
				+ "order by a.a1";
		
		//执行sql语句
		List<String> list = statChartService.executeSQL(sql);
				
		//组织符合要求的xml数据
		String content = generateBarData(list);
		
		//将拼接的字符串写入到data.xml中
		writeXML("stat/chart/onlineinfo/data.xml", content);
				
		
		return "onlineinfo";
	}

	/*
	 * 产品销售的前15名
	 */
	public String productsale() throws FileNotFoundException{
		//oracle
//		String sql = "select * from ( "
//				+ "select product_no, sum(amount) samount "
//				+ "from contract_product_c "
//				+ "group by product_no "
//				+ "order by samount desc) b "
//				+ "where rownum < 16";
		
		//mysql
		String sql = "select product_no, sum(amount) samount "
				+ "from contract_product_c "
				+ "group by product_no "
				+ "order by samount desc "
				+ "limit 0,15";
		
		//执行sql语句
		List<String> list = statChartService.executeSQL(sql);
		//组织符合要求的xml数据
		String content = generateBarData(list);
		
		//将拼接的字符串写入到data.xml中
		writeXML("stat/chart/productsale/data.xml", content);
		
		return "productsale";
	}

	private String generateBarData(List<String> list) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<chart><series>");
		
		int j = 0;
		for(int i = 0; i < list.size(); ++i){
			sb.append("<value xid=\""+ (j++) +"\">" + list.get(i) + "</value>");
			i++;
		}
		sb.append("</series><graphs><graph gid=\"30\" color=\"#FFCC00\" gradient_fill_colors=\"#111111, #1A897C\">");
		
		j = 0;
		for(int i = 0;i < list.size();++i){
			i++;
			sb.append("<value xid=\"" + (j++) + "\">" + list.get(i) + "</value>");
		}
		sb.append("</graph></graphs>");
		sb.append("<labels><label lid=\"0\"><x>0</x><y>20</y><rotate></rotate><width></width><align>center</align><text_color></text_color><text_size></text_size>");
		sb.append("<text>");                  
        sb.append("<![CDATA[<b>产品销量排行榜</b>]]>");
        sb.append("</text>");
        sb.append("</label></labels>");
		sb.append("</chart>");
		return sb.toString();
	}
}
