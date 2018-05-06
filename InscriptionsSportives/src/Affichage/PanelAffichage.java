package Affichage;

import javax.swing.JPanel;

import inscriptions.Inscriptions;

class PanelAffichage extends JPanel {
	private int numPage;
	private Inscriptions inscriptions;
	public PanelAffichage(int num) {
		this.numPage = num;

	}
	
	public void menuequipebis() {
		
	}
	
	public int getNumPage() {
		return numPage;
	}
	
	public void setNumPage(int a) {
		numPage = a;
	}
}