package Repository;

import Exceptions.MyException;
import Model.ADT.List.MyIList;
import Model.ProgramState;

import java.io.IOException;

public interface IRepository {
    void addProgramState(ProgramState newProgramState);
    void logFileProgramState(ProgramState state) throws MyException, IOException;

    MyIList<ProgramState> getProgramStates();
    void setProgramStates(MyIList<ProgramState> programStates);
    ProgramState getOriginalProgramState();
}
