package chess.communication;

import java.math.BigInteger;
import java.util.ArrayList;

import XBeeApI.XBeeException;
import XBeeApI.util.ByteUtils;

public class Command 
{
	//----------------------------------------------------------------------------------------------------------------------------------------------------
		/*	Test Program: Write any test programs for this class in the main method.	*/
	//----------------------------------------------------------------------------------------------------------------------------------------------------	
		public static void main(String[] args) throws XBeeException, InterruptedException
		{
			//Use the following method to test get DestinationAddresses
				Command.SetDestinationAddresses();
//				System.out.println();
//				System.out.println(destinationAddresses);
//				System.out.println();
			//Use the following methods to test ConstructCommand using AT mode
			  /*ConstructCommand("crossSquare",(char)0x1d,(char)0x07);
		  		ConstructCommand("crossSquare",(char)0x01,(char)0x07);
		  		ConstructCommand("execute", (char)0x1d);
		  		ConstructCommand("execute", (char)0x01);
		  		PrintCommandBuffer();
		  		SendCommands();
		  	  */

			//Use the following method to test remote control of the robots
			  //RemoteControlMode(0x01);
		}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------	
		/*	Variables	*/
	//----------------------------------------------------------------------------------------------------------------------------------------------------
		public static ArrayList<String> commandName =  new ArrayList<String>();
		public static ArrayList<char[]> commandBuffer = new ArrayList<char[]>();
	
		/*private static int[][] destinationLow =	{{0x40,0x86,0x96,0x4F},{0x40,0x86,0x96,0xAA},{0x40,0x86,0x96,0x3C},{0x40,0x86,0x96,0x99},
												 {0x40,0x86,0x96,0x9A},{0x40,0x8A,0x87,0xCA},{0x40,0x86,0x96,0xB0},{0x40,0x86,0x96,0xAF},
												 {0x40,0x86,0x96,0x47},{0x40,0x86,0x96,0x4D},{0x40,0x86,0x96,0xB6},{0x40,0x86,0x96,0x39},
												 {0x40,0xB1,0x8B,0x6E},{0x40,0x86,0x96,0x3A},{0x40,0x61,0xB2,0xC5},{0x40,0x86,0x96,0xA8},
												 {0x40,0x86,0x96,0x38},{0x40,0xB8,0x04,0x96},{0x40,0x86,0x96,0xA1},{0x40,0x86,0x96,0x98},
												 {0x40,0x86,0x96,0x3D},{0x40,0x86,0x96,0x49},{0x40,0x86,0x96,0x3B},{0x40,0x86,0x96,0x28},
												 {0x40,0x86,0x96,0x46},{0x40,0x81,0x3E,0x34},{0x40,0x86,0x96,0x97},{0x40,0x86,0x95,0xFE},
												 {0x40,0x8C,0x0D,0xB9},{0x40,0x86,0x96,0x33},{0x40,0x86,0x96,0x48},{0x40,0x86,0x69,0xAE}};*/
		private static int[][] destinationAddresses = new int[32][8];
		
	//----------------------------------------------------------------------------------------------------------------------------------------------------	
		/*	Methods		*/
	//----------------------------------------------------------------------------------------------------------------------------------------------------
		public static void ConstructCommand(String command,char botId,char param1,char param2,char param3,char param4,char param5)
		{
			switch(command.toLowerCase())
			{	
				case("crosssquare"):
					if (param1>=0x01&&param1<=0x07)
					{
						commandName.add("crossSquare");
						char[] temp = {botId,0x01,param1,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					else
					{
						System.out.println("[Invalid crossSquare command] check parameters:\n\t(commandName, botId, newBotId)");
						System.out.println("\tNote:\tyou can only a max of 7 squares at a time."); 
					}
					break;
				case("rotate"):
					if ((param1==0x00||param1== 0x2d) && (param2>=0x00&&param2<=0xFF)&&(param3>=0x00&&param3<=0xFF))
					{
						commandName.add("rotate");
						char[] temp = {botId,0x02,param1,param2,param3,0x00,0x00};
						commandBuffer.add(temp);
					}
					else
					{
						System.out.println("[Invalid rotate command] check parameters:\n\t(commandName, botId, direcion, MSB of degree, LSB of degree)");
						System.out.println("\tNote:\tdirection refers to the sign, either 0 for positive,and - for negative.."); 
					}
					break;
				case("center"):
					if ((param1==0x00||param1==0x2d)&&(param2>=0x00&&param2<=0x04)&&(param3==0x00||param1==0x2d)&&(param4>=0x00&&param4<=0x04))
					{
						commandName.add("center");
						char[] temp = {botId,0x03,param1,param2,param3,param4,param5};
						commandBuffer.add(temp);
					}
					else
					{
						System.out.println("[Invalid center command] check parameters:\n\t(commandName, botId, direction, amount, direction, amount)");
						System.out.println("\tNote:	direction refers to the sign, either 0 for positive,and - for negative.");
						System.out.println("\t\tamount ranges from 0 to 4, because these refer to multiples of 45.");
					}
					break;
				case("writebotid"):
					if (param1>=0x00&&param1<=0xFE)
					{
						commandName.add("writeBotId");
						char[] temp = {botId,0x01,param1,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					else
					{
						System.out.println("[Invalid writeBotId command] check parameters:\n\t(commandName, botId, newBotId)");
						System.out.println("\tNote:\tbot identification can be any value between 0 and 254");
					}
					break;
				case("moveto"):
					if(param1<=0x07&&param2<=0x07)
					{
						commandName.add("movetTo");
						char[] temp = {botId,0x09,param1,param2,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					else
					{
						System.out.println("[Invalid moveTo command] check parameters:\n\t(commandName, x , y)");
						System.out.println("\tNote:\tx and y can be any value between 0 and 7");
					}
					break;
				case("measuresquarestate"):
					{
						commandName.add("measureSquareState");
						char[] temp = {botId,0x05,0x00,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					break;
				case("unwind"):
					{
						commandName.add("unwind");
						char[] temp = {botId,0x06,0x00,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					break;
				case("aligntoedge"):
					{
						commandName.add("alignToEdge");
						char[] temp = {botId,0x07,0x00,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					break;
				case("movedistance"):
					if ((param1==0x00||param1==0x2d)&&(param2>=0x00&&param2<=0xFF)&&(param3>=0x00&&param3<=0xFF)&&(param4>=0x00&&param4<=0xFF)&&(param5>=0x00&&param5<=0xFF))
					{
						commandName.add("moveDistance");
						char[] temp = {botId,0x08,param1,param2,param3,param4,param5};
						commandBuffer.add(temp);
					}
					break;
				case("execute"):
					{
						commandName.add("execute");
						char[] temp = {botId,0xFF,0x00,0x00,0x00,0x00,0x00};
						commandBuffer.add(temp);
					}
					break;
				default:
					System.out.println("\t[Invalid command] Check the command you entered.\n");
			}
		}
	
		public static void ConstructCommand(String command, char botId, char param1,char param2, char param3, char param4)
		{
			ConstructCommand(command,botId,param1,param2,param3,param4,(char)0);
		}
		
		public static void ConstructCommand(String command, char botId, char param1,char param2, char param3)
		{
			ConstructCommand(command,botId,param1,param2,param3,(char)0x00,(char)0x00);
		}
		
		public static void ConstructCommand(String command, char botId, char param1)
		{
			ConstructCommand(command,botId,param1,(char)0x00,(char)0x00,(char)0x00,(char)0x00);
		}
		
		public static void ConstructCommand(String command, char botId)
		{
			ConstructCommand(command,botId,(char)0x00,(char)0x00,(char)0x00,(char)0x00,(char)0x00);
		}
		
		public static void SendCommands()
		{
			CommunicatorAPI.SetUpCommunication("COM26", 57600);
			CommunicatorAPI xbee = new CommunicatorAPI();
			int[] data = new int[6]; 
			int destinationIndex = 0;
			xbee.InitializeCommunication();
				for(int index = 0; index < commandBuffer.size(); index++)
				{
					char[] temp = commandBuffer.get(index);
					if(index == 0)
						destinationIndex = temp[0]-1;
					else
						data[index] = temp[index];
				}
			xbee.SendMessage(data,destinationAddresses[destinationIndex],0x01);
			xbee.EndCommunication();
			commandName.clear();
			commandBuffer.clear();
		}
		
		/*public static void StartUpSequence()*/
		
		public static void SetDestinationAddresses() throws XBeeException, InterruptedException
		{
			CommunicatorAPI.SetUpCommunication("COM17", 57600);
			CommunicatorAPI xbee = new CommunicatorAPI();
			xbee.InitializeCommunication();
			int[] getBotId = {};
			int[]execute = {0xFF,0x00,0x00,0x00,0x00,0x00};
			int[][] tempDestinationAddresses = xbee.nodeAddresses;
			for(int index = 0; index < 2;index++)
			{
				System.out.println(ByteUtils.toBase16(tempDestinationAddresses[index]));
				xbee.SendMessage(getBotId, tempDestinationAddresses[index+1], 1);
				xbee.SendMessage(execute, tempDestinationAddresses[index+1], 1);
				xbee.ReadMessage();
				System.out.println(xbee.input);
			}
			xbee.EndCommunication();
			
		}
		
		public static void PrintCommandBuffer()
		{
			for(int index = 0; index < commandBuffer.size(); index++)	
			{
				String str = new String(commandBuffer.get(index));
				str = String.format("%040x", new BigInteger(1, str.getBytes()));	
				System.out.println("\n"+commandName.get(index)+":\n	"+str.substring(24, str.length()));
			}
		}
		
		public static void RemoteControlMode(int botId)
		{
			RemoteController.SetDestinationLowAddresses(destinationAddresses);
			RemoteController.InitializeControllers();
			RemoteController.SendAPIMoveCommands(botId);
		}
}