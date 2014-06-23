import java.util.HashSet;
import java.util.Set;

public class RoadInfo
{
	long maxMoney,minMoney;
	int id,num;
	String name;
	Set<String> timeSet; 
	public RoadInfo(int id)
	{
		maxMoney=0;
		minMoney=Long.MAX_VALUE;
		this.id=id;
		num=0;
		timeSet=new HashSet<String>();
	}
	
	public void getNewVal(String time,int money)
	{
		if(!timeSet.contains(time))
		{
			num++;
			timeSet.add(time);
		}
		maxMoney=maxMoney<money?money:maxMoney;
		minMoney=minMoney>money?money:minMoney;
	}
}
