
public class SkipListMain {

	public static void main(String[] args) {
		SkipList list = new SkipList();
		list.insert(new SkipListNode(55, true));
		//list.insert(new SkipListNode(75, true)); TODO this breaks in some cases...why?!?!
		list.printSkipList();
	}

}
