import java.util.Random;


public class SkipList {
	
	//our pointers
	public SkipListNode head;
	public SkipListNode tail;
	public int size; //number of nodes with real values
	public int height;
	
	public SkipList(){
		//make the skip list
		
		SkipListNode negInfin0 = new SkipListNode(null, false); //negative infinity
		SkipListNode posInfin0 = new SkipListNode(null, true); //positive infinity
		
		//link layer 0
		negInfin0.right = posInfin0;
		posInfin0.left = negInfin0;
		
		head = negInfin0;
		tail = posInfin0;
		height = 0;
		size = 0;
		
		/*
		//below is a hard coded skip list of testing
		SkipListNode negInfin1 = new SkipListNode(null, false);
		SkipListNode posInfin1 = new SkipListNode(null, true);
		SkipListNode node1 = new SkipListNode(50, true);
		SkipListNode node2 = new SkipListNode(70, true);
		
		//link layer 1
		negInfin0.down = negInfin1;
		posInfin0.down = posInfin1;	
		negInfin1.up = negInfin0;
		posInfin1.up = posInfin0;
		negInfin1.right = node1;
		node1.right = node2;
		node2.left = node1;
		node2.right = posInfin1;
		posInfin1.left = node2;
		node1.left = negInfin1;
		
		height = height + 1;
		size = 1;
		
		SkipListNode negInfin2 = new SkipListNode(null, false);
		SkipListNode posInfin2 = new SkipListNode(null, true);
		SkipListNode node3 = new SkipListNode(20, true);
		SkipListNode node4 = new SkipListNode(50, true);
		SkipListNode node5 = new SkipListNode(60, true);
		SkipListNode node6 = new SkipListNode(70, true);
		
		//link layer2
		negInfin1.down = negInfin2;
		negInfin2.up = negInfin1;
		negInfin2.right = node3;
		node3.left = negInfin2;
		node3.right = node4;
		node4.left = node3;
		node4.up = node1;
		node1.down = node4;
		node4.right = node5;
		node5.left = node4;
		node5.right = node6;
		node6.left = node5;
		node6.right = posInfin2;
		posInfin2.left = node6;
		node6.up = node2;
		node2.down = node6;
		*/
		height = height + 1;
		//size = size + 3; count the size later
	}

	public void printSkipList(){
		SkipListNode p1;
		SkipListNode p2;
		p1 = head; //start at head (vertical)
		p2 = head; //start at head (horizontal)
		while(p1 != null){
			p2 = p1;
			while(p2 != null){
				if(p2.isNull()){
					System.out.print("INF ");
				}
				else{
					System.out.print(" " + p2.value + " ");
				}
				p2 = p2.right;
			}
			p1 = p1.down;
			System.out.println();
		}
	}
	
	public SkipListNode lookup(Integer lookUpVal){
		SkipListNode p1 = head;
		SkipListNode p2 = head;
		while(p1 != null){
			p2 = p1;
			while(p2 != null){
				if(!p2.isNull()){
					if(p2.value > lookUpVal){
						//we know that the value is not in this layer
						break;
					}
					if(p2.value <= lookUpVal && p2.right.isNull() || p2.right.value > lookUpVal){
						//follow down link or return
						if(p2.down != null){
							//follow down link
							//System.out.println("Skipping forward a layer...");
							p1 = p2;
							break;
						}
						else{
							if(p2.value == lookUpVal){
								return p2;
							}
							else{
								//this is where it should be!
								return null;
							}
						}
					}
				}
				p2 = p2.right;
			}
			p1 = p1.down;
		}
		System.out.println("returning null after looking through whole list...");
		return null;
	}
	public void insert(SkipListNode nodeToInsert){
		SkipListNode p1 = head;
		SkipListNode p2 = head;
		while(p1 != null){
			p2 = p1;
			while(p2 != null){
				if(p2 != null){
					/*
					if(p2.right.isNull() && p2.down == null && p2.isNull()){
						//this is the inital case, so insert
						nodeToInsert.right = p2.right;
						nodeToInsert.left = p2;
						p2.right.left = nodeToInsert;
						p2.right = nodeToInsert;
						return;
					}
					*/
					if(!p2.isNull() && p2.value > nodeToInsert.value){
						//we know that the value is not in this layer
						break;
					}
					if((p2.isNull() || p2.value <= nodeToInsert.value) && (p2.right.isNull() || p2.right.value > nodeToInsert.value)){
						//follow down link or return
						if(p2.down != null){
							//follow down link
							//System.out.println("Skipping forward a layer...");
							p1 = p2;
							break;
						}
						else{
							//this is where the node should be inserted!
							nodeToInsert.right = p2.right;
							nodeToInsert.left = p2;
							p2.right.left = nodeToInsert;
							p2.right = nodeToInsert;
							
							Random rand = new Random();
							int coinFlip = rand.nextInt(2);
							
							while(coinFlip == 1){
								//System.out.println(coinFlip);
								//copy up to upper layers!
								if(!p2.isNull()){
									//System.out.println(p2.value);
									while(p2.up == null){
										//go left until you can go up
										p2 = p2.left;
									}
								}
								else{
									if(p2.up == null){
										//System.out.println("p2 up pointer is null");
										//insert a new layer here
										SkipListNode neg = new SkipListNode(null, false);
										SkipListNode pos = new SkipListNode(null, true);
										p2.up = neg;
										neg.down = p2;
										neg.right = pos;
										pos.left = neg;
										
										head = neg;
									}
								}
								
								p2 = p2.up;//go up
								//copy up one layer
								SkipListNode newNode = new SkipListNode(nodeToInsert.value, true);
								newNode.right = p2.right;
								newNode.left = p2;
								p2.right.left = newNode;
								p2.right = newNode;
								newNode.down = nodeToInsert;
								nodeToInsert.up = newNode;
								
								
								//flip coin again
								coinFlip = rand.nextInt(2);
							}
							
							return;
						}
					}
				}
				p2 = p2.right;
			}
			p1 = p1.down;
		}
	}
	
}
