package recipe6;

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser
{
	@Override
	// Phaser类的onAdvance方法在Phaser对象的阶段改变的时候会自动执行.
	protected boolean onAdvance(int phase, int registeredParties)
	// 参数1:当前的阶段数 参数2:注册的参与者数量
	{
		switch (phase)
		{
			case 0:
				return studentsArrived();
			case 1:
				return finishFirstExercise();
			case 2:
				return finishSecondExercise();
			case 3:
				return finishExam();
			default:
				return true;// Phaser已经完成执行并且进入终止态.
		}
	}

	//阶段0:所有学生到达
	private boolean studentsArrived()
	{
		System.out
				.printf("Phaser: The exam are going to start. The students are ready.\n");
		System.out.printf("Phaser: We have %d students.\n",
				getRegisteredParties());
		
		return false;
	}

	//阶段1:所有学生都完成练习1
	private boolean finishFirstExercise()
	{
		System.out
				.printf("Phaser: All the students has finished the first exercise.\n");
		System.out.printf("Phaser: It's turn for the second one.\n");
		return false;
	}

	//阶段2:所有学生都完成练习2
	private boolean finishSecondExercise()
	{
		System.out
				.printf("Phaser: All the students has finished the second exercise.\n");
		System.out.printf("Phaser: It's turn for the third one.\n");
		return false;
	}

	//阶段3:所有学生都完成了练习3,整个任务完成.
	private boolean finishExam()
	{
		System.out.printf("Phaser: All the students has finished the exam.\n");
		System.out.printf("Phaser: Thank you for your time.\n");
		return true;
	}

}
