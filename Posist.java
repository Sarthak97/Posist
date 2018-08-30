import java.util.*;
import java.io.*;
class Node
{
	//Keys of a Node
    Date timestamp;
    int nodeNum;
    String data,NodeID,hashVal;
    Node parent,genesis;
    ArrayList<Node> children;
    
	//counter for Unique ID
	static int counter=1;
    
	//Allowed Value Tracker
	float allowedValue;
	
	//Constructor to create a Node
	Node(String ownerID, float val, String ownerName, Node par, Node root)
    {
        timestamp=new Date();
		if(counter==1)
			allowedValue=val;
		else
			allowedValue=par.allowedValue;
		nodeNum=counter++;
        NodeID=toBinary(nodeNum);
        parent=par;
        children=new ArrayList<Node>();
        genesis=root;
        data=ownerID+"-"+val+"-"+ownerName+"-";
        data+=data.hashCode();
        hashVal=""+this.hashCode();
    }
	
	//Generating NodeID by converting Unique counter to 32 bit binary number
    String toBinary(int n)
    {
    	String ans="";
    	while(n!=0)
    	{
    		ans=n%2+ans;
    		n/=2;
    	}
    	int len=ans.length();
    	while(len!=32)
    	{
    		ans="0"+ans;
    		len++;
    	}
    	return ans;
    }
	
	//Task 2 -> Adding multiple children nodes
    ArrayList<Node> addChildren(Node gen)throws IOException
    {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Node> arr=new ArrayList<Node>();
		float val;
	    	do{
			System.out.println("Enter Owner ID, Value and Owner Name(-1 -1 -1 to exit):");
			String inp[]=br.readLine().split(" ");
			String oID=inp[0];
			val=Float.parseFloat(inp[1]);
			String oName=inp[2];
			if(val>allowedValue)
				System.out.println("This Value is not allowed");
			else
				allowedValue-=val;
			Node obj=new Node(oID,val,oName,this,gen);
			children.add(obj);
			arr.add(obj);
		}while(val!=-1);
		return arr;
    }
	
	//Task 3 -> Adding a child node for a particular parent
    Node addChild(Node gen)throws IOException
    {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Owner ID, Value and Owner Name:");
		String inp[]=br.readLine().split(" ");
		String oID=inp[0];
		float val=Float.parseFloat(inp[1]);
		String oName=inp[2];
		if(val>allowedValue)
			System.out.println("This Value is not allowed");
		else
			allowedValue-=val;
		Node obj=new Node(oID,val,oName,this,gen);
		children.add(obj);
		return obj;
    }
}

class Main
{
	public static void main(String args[])throws IOException
    {
    	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		//Task 1 -> Creating Genesis Node
		System.out.println("Creating Genesis Node:");
		System.out.println("Enter Owner ID, Value and Owner Name:");
		String inp[]=br.readLine().split(" ");
		String oID=inp[0];
		float val=Float.parseFloat(inp[1]);
		String oName=inp[2];
		Node genesis=new Node(oID,val,oName,null,null);
		genesis.genesis=genesis;
		
		//Task 3
		System.out.println("TASK 3");
		HashMap<Integer,Node> allNodes=new HashMap<Integer,Node>();
		allNodes.put(genesis.nodeNum,genesis);
		int num=0;
		do
		{
			System.out.println("Enter Node Number whose children are to be inserted.(-1 to stop):");
			num=Integer.parseInt(br.readLine());
			if(allNodes.containsKey(num))
			{
				Node currPar=(Node)allNodes.get(num);
				Node inserted=currPar.addChild(genesis);
				allNodes.put(inserted.nodeNum,inserted);
			}
			else if(num!=-1)
			{
				System.out.println("Invalid Node Number. Try again!");
			}
		}while(num!=-1);
		
		//Task 2 -> Add Children in sets
		System.out.println("TASK 2");
		num=0;
		do
		{
			System.out.println("Enter Node Number whose children are to be inserted.(-1 to stop):");
			num=Integer.parseInt(br.readLine());
			if(allNodes.containsKey(num))
			{
				Node currPar=(Node)allNodes.get(num);
				ArrayList<Node> inserted=currPar.addChildren(genesis);
				for(Node i : inserted)
					allNodes.put(i.nodeNum,i);
			}
			else if(num!=-1)
			{
				System.out.println("Invalid Node Number. Try again!");
			}
		}while(num!=-1);
		  
		//Displaying the complete tree level by level
		System.out.println("Printing the tree");
		Stack<Node> st=new Stack<Node>();
		st.push(genesis);
		while(!st.isEmpty())
		{
			Node currPar=st.pop();
			ArrayList<Node> ch=currPar.children;
			for(Node chh:ch)
			{
			  System.out.println(chh.timestamp+"\n"+chh.nodeNum+"\n"+chh.NodeID+"\n"+chh.parent+"\n"+chh.data+"\n"+chh.hashVal+"\n"+chh.genesis+"\n"+chh.children);
			  st.push(chh);
			}
		}
    }
}
