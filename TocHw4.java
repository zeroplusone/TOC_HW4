/******************************************************
Filename	: TocHw4.java
Programmer	: F74004088 ��T�T�A �L�μ� Chia-Ying Lin
Purpose		: We use real price housing information in our country, and find out ��which road in a city has house trading records spread in #max_distinct_month��.
Input		: You can find the data schema here:
			  http://www.datagarage.io/datasets/ktchuang/realprice/A
Output		: standard output
Compilation	: ant -buildfile /home/TOC/ANT/toc4/build.xml build -Darg F74004088
Run			: ex.
			   java �Vjar TocHW4.jar URL
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
		Pattern pat1=Pattern.compile("(.*((��)|(��)|(�j�D)))");
		Pattern pat2=Pattern.compile(".*��");
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
				inRoad=item.get("�g�a�Ϭq��m�Ϋت��Ϫ��P").toString();
				mat1=pat1.matcher(inRoad);
				mat2=pat2.matcher(inRoad);
				
				//�� �� �j�D
				if(mat1.find(0))
				{		
					inRoad=mat1.group(0);
					inMoney=Integer.parseInt(item.get("�`����").toString());
					if(map.containsKey(inRoad))
					{						
						inId=map.get(inRoad).id;
						tmpRoad=road.get(inId);
						tmpRoad.getNewVal(item.get("����~��").toString(),inMoney);					
					}
					else
					{
						tmpRoad=new RoadInfo(nowId);
						nowId++;
						tmpRoad.name=inRoad;
						tmpRoad.getNewVal(item.get("����~��").toString(),inMoney);
						road.add(tmpRoad);
						map.put(inRoad,tmpRoad);
					}
					findMaxNum=findMaxNum<tmpRoad.num?tmpRoad.num:findMaxNum;
				}
				//��
				else if(mat2.find(0))
				{
					inRoad=mat2.group(0);
					inMoney=Integer.parseInt(item.get("�`����").toString());
					if(map.containsKey(inRoad))
					{						
						inId=map.get(inRoad).id;
						tmpRoad=road.get(inId);
						tmpRoad.getNewVal(item.get("����~��").toString(),inMoney);					
					}
					else
					{
						tmpRoad=new RoadInfo(nowId);
						nowId++;
						tmpRoad.name=inRoad;
						tmpRoad.getNewVal(item.get("����~��").toString(),inMoney);
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
					System.out.println(road.get(i).name+", �̰������: "+road.get(i).maxMoney+", �̧C�����: "+road.get(i).minMoney);
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
