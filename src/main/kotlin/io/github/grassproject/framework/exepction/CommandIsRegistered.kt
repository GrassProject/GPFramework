package io.github.grassproject.framework.exepction

class CommandIsRegistered: Exception("This Command is Already Registered")
class CommandIsNotRegistered: Exception("This Command is Not Registered")
