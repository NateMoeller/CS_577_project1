
public class SkipListNode {
	public Integer value;
	
	//our pointers
	public SkipListNode up;
	public SkipListNode down;
	public SkipListNode left;
	public SkipListNode right;
	
	public boolean positive;
	
	public SkipListNode(Integer val, boolean pos){
		this.value = val;
		this.positive = pos;
	}
	
	public boolean isNull(){
		if(this.value == null){
			return true;
		}
		return false;
	}
	
}
