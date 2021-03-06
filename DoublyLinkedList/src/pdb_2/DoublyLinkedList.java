package pdb_2;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class DoublyLinkedList {

	Scanner sc = new Scanner(System.in);
	private Node header;
	private int size;
	public DoublyLinkedList() {
		header = new Node(null);
		size=0;
	}
	
	
	private class Node{
		
		private List data;
		private Node previousNode;
		private Node nextNode;
		
		Node(List data){
			if(data == null)
				this.data = null;
			else {//data=data.list는 주소를 가져오므로 temp라는 리스트를 만들어서 새로 저장한뒤 데이터에 저장한다.
				List temp = new ArrayList();
				int size = data.size();
				for(int i=0;i<size;i++) {
					temp.add(data.get(i));
				}
				this.data = temp;
			}
			this.previousNode = null;
			this.nextNode = null;
		}
	
	}
	
	void addData(int index,List datalist) {
		add(index,datalist);
	}
	
	//index위치에서 얻은 노드의 데이터를 반환한다.
	public Object get(int index) {
		return getNode(index).data;
	}
	
	//첫번째 노드를 반환한다.
	public Object getFirst() {
		return get(0);
	}
	
	private Node getNode(int index) {
		
		if(index<0 || index>size) {
			throw new IndexOutOfBoundsException("Index : "+index+", Size : " +size);
			
		}
		
		Node node = header;
		
		
		//index가 리스트 size의 중간 앞이면 순차적으로 탐색한다.
		if(index<(size/2)) {
			
			for(int i=0; i<=index; i++) {
				node = node.nextNode;
			}
			
		}else {
			//index가 리스트 size의 중간보다 뒤면 뒤에서부터 탐색한다.
				
			for(int i= size; i> index; i--) {
				node = node.previousNode;
			}
		}
			
			
		return node;
			
	}
	
		
	//obj 데이터와 같은 노드의 위치를 반환한다.
	public int getNodeIndex(Object obj) {
		
		if(size<=0) {
			return -1;
		}
		
		int index = 0;
		//첫번째 노드를 가져온다.
		Node node = header.nextNode;
		List nodeDate = node.data;
		
		//첫번째 노드부터 같은 데이터를 가진 노드를 탐색한다.
		while(!nodeDate.contains(obj)) {
			
			
			node = node.nextNode;
			
			if(node==null) {
				return -1;
			}
			
			
			nodeDate = node.data;
			index++;
		}
		
		//위치를 반환한다.
		return index;
	}
	
	//리스트의 첫번째에 데이터를 삽입한다.
	
	public void addFirst(List data) {
		
		//데이터를 담은 새로운 노드 생성
		Node newNode = new Node(data);
		
		//새로운 노드가 다음 노드로 첫 번째 노드를 가리킨다.
		newNode.nextNode = header.nextNode;
		
		//리스트가 비어있지 않으면
		if(header.nextNode !=null) {
			
			//첫 노드가 자신의 앞 노드로 새로운 노드를 가리킨다.
			header.nextNode.previousNode = newNode;
			
		}else {//리스트가 비어있으면
			
			//헤더가 마지막 노드를 새로운 노드로 가리키도록 한다.
			header.previousNode = newNode;
		}
		
		//헤더가 첫번째 노드로 새로운 노드를 가리키도록 한다.
		header.nextNode = newNode;
		size++;
	
	}
	
	public void add(int index, List data) {
		
		//index가 0이면 addFirst() 함수를 호출한다.
		if(index ==0) {
			addFirst(data);
			return;
		}
		
		//삽입할 index위치의 앞 노드를 가져온다.
		Node previous = getNode(index-1);
		
		//삽입할 index의 위치의 다음 노드를 가져온다.
		Node next = previous.nextNode;
		
		//data로 새로운 노드 생성
		Node newNode = new Node(data);
		
		//앞노드가 새로운 노드를 다음노드로 가리킨다.
		previous.nextNode = newNode;
		
		//새로운 노드가 앞노드를 이전노드로 가리킨다.
		newNode.previousNode = previous;
		
		//새로운 노드의 다음 노드에 다음노드를 지정한다.
		newNode.nextNode = next;
		
		//삽입위치가 마지막 위치가 아니면
		if(newNode.nextNode != null) {
			
			//다음 노드가 새로운 노드를 앞노드로 지정한다.
			next.previousNode = newNode;
			
		}else {//삽입 위치가 마지막 이면
			
			//헤더가 가리키는 마지막 노드가 새로운 노드가 된다. 
			header.previousNode = newNode;
		}
	
		size++;
	}
	
	//마지막 노드를 반환한다.
	public void addLast(List data) {
		add(size,data);
	}
	
	//data를 마지막에 넣는다.
	public void add(List data) {
		addLast(data);
	}
	
	public Object removeFirst() {
		
		//첫번째 노드를 가져온다.
		Node firstNode = getNode(0);
		
		//헤더가 첫 노드로 두번째 노드를 가리킨다.
		header.nextNode = firstNode.nextNode;
		
		//리스트가 비어있지 않을때
		if(header.nextNode != null) {
			
			//두번째 노드가 가리키는 앞 노드는 없다.
			firstNode.nextNode.previousNode = null;
			
		}else {//리스트가 비게 되면
			
			//헤더가 가리키는 마지막 노드가 없다.
			header.previousNode = null;
			
		}
		
		size--;
		//첫번째 노드의 데이터를 반환
		return firstNode.data;
	}
	
	public Object remove(int index) {
		
		if(index<0 || index>=size) {
			throw new IndexOutOfBoundsException("Index : "+index+", Size:"+size);
		}else if(index==0) {
			return removeFirst(); //index가 0이면 첫번째 데이터를 제거
		}
		
		
		//제거할 index위치의 노드를 가져온다.
		Node removeNode = getNode(index);
		//제거할 노드의 앞노드를 가져온다
		Node previous = removeNode.previousNode;
		//제거할 노드의 뒷노드를 가져온다.
		Node next = removeNode.nextNode;
		
		//앞노드가 다음노드를 다음으로 가리킨다.
		previous.nextNode = next;
		
		//제거되는 노드가 마지막 노드가 아니면 
		if(next!=null) {
			
			//제거 노드의 뒷노드가 앞노드로 제거 노드 앞 노드를 가리킨다.
			next.previousNode = previous;
			
		}else {//제거 노드가 마지막 노드면
			
			//헤더가 마지막 노드로  앞노드를 가리킨다.
			header.previousNode = previous;
		}
		
		size--;
		
		//제거노드의 데이터를 반환
		return removeNode.data;
		
	}
	
	//데이터를 제거한다.
	public boolean remove(Object data) {
		//data가 있는 index를 얻는다.
		int nodeIndex = getNodeIndex(data);
		
		//데이터가 없으면 false 반환
		if(nodeIndex == -1) {
			return false;
		}else {//데이터가 있으면 제거
			remove(nodeIndex);
			return true;
		}
	}
	
	public Object removeLast() {
		return remove(size-1);
	}
	
	
	public int size() {
		return size;
	}
	
	
	public void look(FieldsArray fa) {
		if(size == 0) {
			System.out.println("Empty!");
			return;
		}
		Node node = header;
		List data;
	
		for(int j=0;j<size;j++) {
			node = node.nextNode;
			data = node.data;
			int dsize = data.size();
			for(int i=0;i<dsize;i++) {
				if(i==0) {
					System.out.println();
				}
				System.out.print(fa.name[i]+":"+data.get(i)+" ");
			}
		}
	}	
	
	public Node search(FieldsArray fa) {
		if(size==0) {
			System.out.println("Empty");
			return null;
		}
		Node node=header;
		List data;
		String searchd=sc.nextLine();
		 // for(int j=0;j<size;j++) {
			node=node.nextNode;
			int index=getNodeIndex(searchd);
			node = getNode(index);
			//List nodeDate = node.data;
			//Object obj=searchd;
			//if(nodeDate.contains(obj)) {
			data = node.data;
			int dsize = data.size();
			for(int i=0;i<dsize;i++) {
				System.out.println(fa.name[i]+":"+data.get(i));
			//}
		  //}
		}
		return node;
	}
	public void modify(FieldsArray fa) {
		if(size==0) {
			System.out.println("Empty");
			return;
		}
		Node node=header;
		List data;
		node=search(fa);
		System.out.println("수정할 field의 index를 선택하세요.(0,1,...):");
		int modif = sc.nextInt();
		sc.nextLine();
		System.out.println("데이터를 입력하세요.");
		String modid = sc.nextLine();
		node.data.set(modif,modid); //리스트에 있는 데이터 교환
		data = node.data;
		int dsize = data.size();
		for(int i=0;i<dsize;i++) {//수정된 데이터 보여주기
			System.out.println(fa.name[i]+":"+data.get(i));
		}
	}

		
}
	    

	

