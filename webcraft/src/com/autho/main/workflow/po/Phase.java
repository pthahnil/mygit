package com.autho.main.workflow.po;

public class Phase {
	private int id;//主键
	private String phaseName;//类似描述
	private String phaseKey;//关联时的Key
	
	private String options;//里面放一个map之类的吧
	private String phaseType;//类似初始，中间，完毕这一类的
}
