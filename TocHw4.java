/******************************************************
Filename	: TocHw4.java
Programmer	: F74004088 資訊三乙 林佳瑩 Chia-Ying Lin
Purpose		: We use real price housing information in our country, and find out “which road in a city has house trading records spread in #max_distinct_month”.
Input		: You can find the data schema here:
			  http://www.datagarage.io/datasets/ktchuang/realprice/A
Output		: standard output
Compilation	: ant -buildfile /home/TOC/ANT/toc4/build.xml build -Darg F74004088
Run			: ex.
			   java –jar TocHW4.jar URL
Date		: 2014/06/21
*******************************************************/

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.*;

public class TocHw4 
{


	public static void main(String[] args) 
	{

		if(args.length < 1)
		{
			System.out.println("<Error> no URL!");
			return;
		}
		String url;
		url=args[0];
		
	//====variable====
		Pattern pat1=Pattern.compile("(.*((路)|(街)|(大道)))");
		Pattern pat2=Pattern.compile(".*巷");
		Matcher mat1,mat2;
		String inRoad;
		long findMaxNum=0;
		int inMoney,inId,nowId=0;
		Map<String,RoadInfo> map=new HashMap<String,RoadInfo>();
		ArrayList<RoadInfo> road=new ArrayList<RoadInfo>();
		RoadInfo tmpRoad;
	//====read data====
		try 
		{
			InputStreamReader in=new InputStreamReader(new URL(url).openStream(),"UTF-8");
			JSONArray data=new JSONArray(new JSONTokener(in));
			in.close();
			for(int i=0;i<data.length();++i)
			{
				JSONObject item=data.getJSONObject(i);
				inRoad=item.get("土地區段位置或建物區門牌").toString();
				mat1=pat1.matcher(inRoad);
				mat2=pat2.matcher(inRoad);
				
				//路 街 大道
				if(mat1.find(0))
				{		
					inRoad=mat1.group(0);
					inMoney=Integer.parseInt(item.get("總價元").toString());
					if(map.containsKey(inRoad))
					{						
						inId=map.get(inRoad).id;
						tmpRoad=road.get(inId);
						tmpRoad.getNewVal(item.get("交易年月").toString(),inMoney);					
					}
					else
					{
						tmpRoad=new RoadInfo(nowId);
						nowId++;
						tmpRoad.name=inRoad;
						tmpRoad.getNewVal(item.get("交易年月").toString(),inMoney);
						road.add(tmpRoad);
						map.put(inRoad,tmpRoad);
					}
					findMaxNum=findMaxNum<tmpRoad.num?tmpRoad.num:findMaxNum;
				}
				//巷
				else if(mat2.find(0))
				{
					inRoad=mat2.group(0);
					inMoney=Integer.parseInt(item.get("總價元").toString());
					if(map.containsKey(inRoad))
					{						
						inId=map.get(inRoad).id;
						tmpRoad=road.get(inId);
						tmpRoad.getNewVal(item.get("交易年月").toString(),inMoney);					
					}
					else
					{
						tmpRoad=new RoadInfo(nowId);
						nowId++;
						tmpRoad.name=inRoad;
						tmpRoad.getNewVal(item.get("交易年月").toString(),inMoney);
						road.add(tmpRoad);
						map.put(inRoad,tmpRoad);
					}
					findMaxNum=findMaxNum<tmpRoad.num?tmpRoad.num:findMaxNum;
				}
			}
			//====print answer====
			for(int i=0;i<road.size();++i)
			{
				if(road.get(i).num==findMaxNum)
				{
					System.out.println(road.get(i).name+", 最高成交價: "+road.get(i).maxMoney+", 最低成交價: "+road.get(i).minMoney);
				}
			}
			
		} catch (JSONException e) 
		{	System.out.println("<Error> JSONException!");
			System.out.println(e.getMessage());
		} catch (MalformedURLException e) 
		{	System.out.println("<Error> MalformedURLException!");
			System.out.println(e.getMessage());
		} catch (IOException e) 
		{	System.out.println("<Error> IOException!");	
			System.out.println(e.getMessage());
		}
	

	}
}
