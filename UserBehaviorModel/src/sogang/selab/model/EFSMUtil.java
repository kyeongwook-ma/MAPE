package sogang.selab.model;

import java.util.List;

/**
 * @author se
 * EFSM �ٷ�� Ŭ����
 * K �� GK-tail�� �����
 */
public class EFSMUtil {

	/**
	 * EFSM �� GK-tail�˰������� ���ս�Ų��.
	 * @param src 
	 * @param dst
	 * @throws Exception 
	 */
	public static AFSM gkTail(AFSM src, AFSM dst, int k) throws Exception {

		if(src == null || dst == null) throw new Exception();

		AFSM mergedEFSM = new AFSM();

		int range = minSize(src, dst) + 1;

		// if(range < 0 || k > range) throw new IndexOutOfBoundsException();

		for(int i = 0; i < range - k; ++i) {			
			for(int j = i; j < i + k;  ++j) {
				Transition srcTran = src.get(j);
				Transition dstTran = dst.get(j);

				if(srcTran.equals(dstTran)) {
					
					srcTran.setDst(dstTran.getSrc());
					dstTran.setSrc(srcTran.getSrc());
					
					mergedEFSM.addStateSeq(srcTran);
				}
			}
		}
		if(mergedEFSM.size() == 0) 
			return src;
		return mergedEFSM;
	}
	
	private static int minSize(AFSM src, AFSM dst) {
		return src.size() > dst.size() ? dst.size() : src.size();
	}

}
