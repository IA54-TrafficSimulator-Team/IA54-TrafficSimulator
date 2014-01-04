from org.janusproject.kernel.message import StringMessage
def liveAgent(agent) :
        mess = StringMessage("hello")
        agent.broadcastMessage(mess)
        print mess
	agent.killMe()
