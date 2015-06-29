package edu.utdallas.robotchess.robotcommunication.commands;

public class ReadLocationCommand extends Command
{
    public ReadLocationCommand(int robotID)
    {
        commandID = 0x6;
        payloadLength = 0x1;
        this.robotID = robotID;
    }

    @Override
    public int[] generatePayload()
    {
        int payload[] = new int[payloadLength];

        payload[0] = commandID;

        return payload;
    }
}
