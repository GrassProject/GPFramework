package io.github.grassproject.framework.exepction

class CommandAlreadyRegistered: Exception("This Command is Already Registered")
class CommandIsNotRegistered: Exception("This Command is Not Registered")
