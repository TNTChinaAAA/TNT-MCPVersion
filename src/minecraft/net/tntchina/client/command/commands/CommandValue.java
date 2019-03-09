package net.tntchina.client.command.commands;

import net.tntchina.client.command.*;
import net.tntchina.client.main.*;
import net.tntchina.client.module.*;
import net.tntchina.client.value.*;
import net.tntchina.client.value.values.ModeValue;
import net.tntchina.client.value.values.NumberValue;

/**
 * the command on process changed module value.(这个命令被激活时会更改功能的变量)
 * @author TNTChina
 */
public class CommandValue extends Command {
	
	private static String ERROR = Color + "r" + Color + "a";
	private static String error = Color + "c" + Color + "l";
	
	public CommandValue() {
		super("value");
	}

	public void execute(String[] strings) {
        if(strings.length > 3) {
            Module module = ModuleManager.getModule(strings[1]);

            if(module == null) {
                TNTChina.displayMessage(error + "Error: "+ ERROR + "The entered module not exist.");
                return;
            }

			Value value = module.getValue(strings[2]);

            if(value == null) {
            	TNTChina.displayMessage(error + "Error: " + ERROR + "The entered value not exist.");
                return;
            }

            if (value instanceof ModeValue) {
            	ModeValue mode = (ModeValue) value;
            	String newValue = strings[3];
            	boolean flag = false;
            	
            	for (String str : mode.getStrings()) {
            		if (str.equalsIgnoreCase(newValue)) {
            			newValue = str;
            			flag = true;
            			break;
            		}
            	}
            	
            	if (!flag) {
            		TNTChina.displayMessage("The mode not not exist in " + module.getName() + ".");
            		return;
            	}
            	
            	mode.setObject(newValue);
                TNTChina.displayMessage(Color + "cThe mode of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
            	return;
            }
            
            if (value.getObject() instanceof Number) {
            	if(value.getObject() instanceof Float) {
                	final float newValue = Float.parseFloat(strings[3]);
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                    TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
                
                if (value.getObject() instanceof Integer) {
                	int newValue = Integer.parseInt(strings[3]);
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
                
                if (value.getObject() instanceof Short) {
                	short newValue = Short.parseShort(strings[3]);
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
                
                if (value.getObject() instanceof Double) {
                	double newValue = Double.parseDouble(strings[3]);
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
                
                if (value.getObject() instanceof Byte) {
                	byte newValue = Byte.parseByte(strings[3]);
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
                
                if (value.getObject() instanceof Long) {
                	long newValue = 0L;
                	
                	try {
                    	newValue = Long.parseLong(strings[3]);
                	} catch (Exception e) {
                		TNTChina.displayMessage(this.getBigString(Color + "c<Value>: <Error> Your number format is wrong."));
                	}
                	
                	if (value instanceof NumberValue) {
                   		NumberValue numValue = (NumberValue) value;
                		
                   		if (newValue > numValue.getMaxValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very big.");
                			return;
                		} else if (newValue < numValue.getMinValue()) {
                			TNTChina.displayMessage(error + "Error: " + ERROR + "The value is very small.");
                			return;
                		}
                	}
                	
                	value.setObject(newValue);
                	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
                }
            }
            
            if (value.getObject() instanceof Boolean) {
            	boolean newValue = Boolean.parseBoolean(strings[3]);
            	value.setObject(newValue);
            	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + newValue + Color + "c.");
            }
            
            if (value.getObject() instanceof String) {
            	String str = strings[3];
            	value.setObject(str);
            	TNTChina.displayMessage(Color + "cThe value of " + Color + "a" + Color + "l" + module.getName() + " " + Color + "8(" + Color + "a" + Color + "l" + value.getValueName() + ") " + Color + "c was set to " + Color + "a" + Color + "l" + str + Color + "c.");
            }
            
            return;
        }

        TNTChina.displayMessage(Color + "c" + Color + "l<Value>: " + Color + "r" + Color + "a<module> <valuename> <new_value>.");
    }
}
