package com.sx.frontend.protocal376_1.internal.processControl;


import org.squirrelframework.foundation.fsm.Condition;
import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;



/**
 * Created by PETER on 2015/3/27.
 */
@States({
        @State(name= ProtocalState.WAIT_RESPONSE),
        @State(name= ProtocalState.WAIT_NEXT_DECODE),
        @State(name= ProtocalState.IDLE),
})

@Transitions({
        //开始解码
        @Transit(from= ProtocalState.IDLE, to= ProtocalState.IDLE,
                on=Action.DECODE),
        //开始编码
        @Transit(from= ProtocalState.IDLE, to= ProtocalState.WAIT_RESPONSE,
                on=Action.ENCODE),
        //主动上传，忽略
        @Transit(from= ProtocalState.WAIT_RESPONSE, to= ProtocalState.WAIT_RESPONSE,
                on=Action.DECODE,when=InitiativeUploadCondition.class),
        //收到回复，没有多帧
        @Transit(from= ProtocalState.WAIT_RESPONSE, to= ProtocalState.IDLE,
                on=Action.DECODE,when=SingleEncodeCondition.class),
        //等待多帧解码
        @Transit(from= ProtocalState.WAIT_RESPONSE, to= ProtocalState.WAIT_NEXT_DECODE,
                on=Action.DECODE,when=MultipleDecodeCondition.class),
        //等待多帧解码
        @Transit(from= ProtocalState.WAIT_NEXT_DECODE, to= ProtocalState.WAIT_NEXT_DECODE,
                on=Action.DECODE,when=MultipleDecodeCondition.class),
        //完成最后一帧解码
        @Transit(from= ProtocalState.WAIT_NEXT_DECODE, to= ProtocalState.IDLE,
                on=Action.DECODE,when=SingleEncodeCondition.class),


})

public class ProcessControl extends
        AbstractStateMachine<ProcessControl,String,String,Integer> {
        public void encode(String from, String to, String event, Integer context) throws Exception{

        }

}

class SingleEncodeCondition implements Condition<Integer> {

        @Override
        public boolean isSatisfied(Integer context) {
                return false;
        }

        @Override
        public String name() {
                return "EncodeCondition";
        }
}

class MultipleDecodeCondition implements Condition<Integer>{

        @Override
        public boolean isSatisfied(Integer context) {
                return false;
        }

        @Override
        public String name() {
                return "DecodeCondition";
        }
}

class InitiativeUploadCondition implements Condition<Integer>{
        @Override
        public boolean isSatisfied(Integer context) {
                return false;
        }

        @Override
        public String name() {
                return "InitiativeUploadCondition";
        }
}

