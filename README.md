# Lowpan-Visualizer
This is a visualizer that displays what nodes on the network report about their position. It listens on port `34217` because that's what the random number generator chose.

## Packet format

The visualizer listens for packets describing a lowpan node, the format of the packet is:

    name,rank,parentName,parentRank

As a string. For a node that does not have a parent, the format is

    name,rank

If the node is already on the network, this will remove it from the network. What this means is that *The DODAG root should only report its existence once, or everything will be borked*

If the node has never had a parent, it should not report its existence since it will be considered the DODAG

### Pseudo-code operation of a client

    boolean hadAParent = false
    while(true):
	    if(hasAParent):
	        sendPacket("my_IP,my_rank,parent_IP,parent_rank", port 34217)
	        hadAParent = true
	    elseif (hadAParent): #Used to have a parent and now it doesn't
	        sendPacket("my_IP,my_rank", port 34217)
	    endif
    endwhile
