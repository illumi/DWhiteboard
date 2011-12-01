program WHITEBOARD_PROG {
  version WHITEBOARD_VERS { void sendMessage(String)   = 1;
                       String readMessage(void) = 2;
					   void close(void) = 3;
  } = 1;
} = 0x6a3b7ca0;