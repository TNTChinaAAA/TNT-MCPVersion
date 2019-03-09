package net.tntchina.client.event.events;

import net.tntchina.client.event.Event;

/**
 * set the player has safe walk.(�������Ҫ��Ҫ��ֹ�ӷ����Ե����)
 * @author TNTChina
 */
public class SafeWalkEvent extends Event {
	
    private boolean safe;

    public SafeWalkEvent(boolean safe) {
        this.safe = safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }
    
    public boolean getSafe() {
    	return this.safe;
    }
}
