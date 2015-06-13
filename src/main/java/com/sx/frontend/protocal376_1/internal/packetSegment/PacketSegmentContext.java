package com.sx.frontend.protocal376_1.internal.packetSegment;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by PETER on 2015/3/25.
 */
public class PacketSegmentContext {
    private Map<SegmentEnum,Segment> packetSegmentMap=new LinkedHashMap<>();
    public PacketSegmentContext(){
        packetSegmentMap.put(SegmentEnum.head,new Head());
        packetSegmentMap.put(SegmentEnum.control,new Control());
        packetSegmentMap.put(SegmentEnum.data,new Data());
        packetSegmentMap.put(SegmentEnum.auxiliary,new Auxiliary());
        packetSegmentMap.put(SegmentEnum.tail,new Tail());
    }

    public Iterator<Segment> getIterator(){
        return packetSegmentMap.values().iterator();
    }

    public Segment getSegment(SegmentEnum segmentEnum){
        return packetSegmentMap.get(segmentEnum);
    }


    public void reset(){
        for(Segment seg:packetSegmentMap.values()){
            seg.reset();
        }
    }
}
