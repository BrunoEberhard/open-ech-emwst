/*
	Copyright (C) 2017, Bruno Eberhard, bruno.eberhard@pop.ch
	
	This file is part of Open-eCH.
	
	Open-eCH is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as
	published by the Free Software Foundation, either version 3 of the
	License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.
	
	You should have received a copy of the GNU Affero General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package ch.openech;

import java.util.Arrays;
import java.util.List;

import org.minimalj.application.Application;
import org.minimalj.frontend.action.Action;
import org.minimalj.frontend.action.ActionGroup;
import org.minimalj.frontend.impl.nanoserver.NanoWebServer;

import ch.openech.frontend.NewVATDeclarationEditor;
import ch.openech.frontend.VATDeclarationXmlImport;
import ch.openech.model.emwst.EffectiveReportingMethod;
import ch.openech.model.emwst.FlatTaxRateMethod;
import ch.openech.model.emwst.NetTaxRateMethod;
import ch.openech.model.emwst.VATDeclaration;

public class EmwstApplication extends Application {
	
	@Override
	public List<Action> getNavigation() {
		ActionGroup newActions = new ActionGroup("Neu erstellen");
		newActions.add(new NewVATDeclarationEditor(EffectiveReportingMethod.class));
		newActions.add(new NewVATDeclarationEditor(NetTaxRateMethod.class));
		newActions.add(new NewVATDeclarationEditor(FlatTaxRateMethod.class));

		ActionGroup importActions = new ActionGroup("Import");
		importActions.add(new VATDeclarationXmlImport());

		return Arrays.asList(newActions, importActions);
	}

	@Override
	public Class<?>[] getEntityClasses() {
		return new Class<?>[] { VATDeclaration.class };
	}
	
	public static void main(String[] args) {
		NanoWebServer.start(new EmwstApplication());
		// Swing.start(new EmwstApplication());
	}
}
