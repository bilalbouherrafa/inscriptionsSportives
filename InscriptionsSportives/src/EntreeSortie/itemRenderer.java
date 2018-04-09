package EntreeSortie;

import commandLineMenus.ListItemRenderer;

public class itemRenderer<Equipe> implements ListItemRenderer<Equipe>
{
	@Override
	public String title(int index, Equipe item)
	{
		//return item.getNom();
		return "test";
	}

	@Override
	public String shortcut(int index, Equipe item)
	{
		return "" + (index + 1);
	}

	@Override
	public String empty()
	{
		return "No item to select.\n";
	}


}
