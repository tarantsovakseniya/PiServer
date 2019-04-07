package Dialogue.ServerAnswear;

import Dialogue.Root;

import java.io.DataOutputStream;

public interface CommandsWork {
    void execute(Root root, DataOutputStream dataOutputStream);
}
