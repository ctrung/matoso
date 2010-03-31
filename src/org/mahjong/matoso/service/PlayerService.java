/* MATOSO project - 2009
 *
 * This acronym stands for MAhjong TOurnament SOftware.
 * Originally created by Nicolas Pochic and Clement Trung.
 * Feel free to modify and redistribute this code wherever you want.
 * Software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.mahjong.matoso.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.mahjong.matoso.bean.Player;
import org.mahjong.matoso.bean.Tournament;
import org.mahjong.matoso.constant.ApplicationCst;
import org.mahjong.matoso.constant.BundleCst;
import org.mahjong.matoso.constant.RequestCst;
import org.mahjong.matoso.util.HibernateUtil;
import org.mahjong.matoso.util.I18nUtils;
import org.mahjong.matoso.util.NumberUtils;
import org.mahjong.matoso.util.exception.FatalException;
import org.mahjong.matoso.util.exception.imports.ImportException;
import org.mahjong.matoso.util.importing.ImportUtils;
import org.mahjong.matoso.util.importing.XLSFile;
import org.mahjong.matoso.util.message.MatosoMessage;
import org.mahjong.matoso.util.message.MatosoMessages;

/**
 * Methods for players.
 * 
 * @author ctrung
 * @date 04 Dec. 2009
 */
public abstract class PlayerService {

	private static Logger LOGGER = Logger.getLogger(PlayerService.class);
	
	/**
	 * Find all players participating in a tournament.
	 * 
	 * @param tournament The tournament.
	 * @return A non null list of players.
	 * 
	 * @throws FatalException
	 */
	@SuppressWarnings("unchecked")
	public static List<Player> getListFromTournament(Tournament tournament) throws FatalException {
		try {
			Query q = HibernateUtil.getSession().createQuery(
					"from Player p inner join fetch p.tournaments ts where :t in ts order by p.lastname");
			q.setParameter("t", tournament);

			List<Player> players = q.list();
			
			// iterate to add team in player
			// TODO : add a proper mapping for Team property on Player
			for(Player p : players) {
				p.setTeam(TeamService.getTeamForPlayer(p, tournament));
			}
			return players;
		} catch (HibernateException e) {
			throw new FatalException(e);
		}
	}
	
	/**
	 * Get a player based on its id.
	 * 
	 * @param id 
	 * @return 
	 * 
	 * @throws FatalException
	 */
	public static Player getById(Integer id) throws FatalException {
		
		if(id==null) return null;
		
		try {
			Query q = HibernateUtil.getSession().createQuery(
					"from Player p where p.id = :id");
			q.setParameter("id", id);

			return (Player) q.uniqueResult();
			
		} catch (HibernateException e) {
			throw new FatalException(e);
		}
	}

	/**
	 * Return the players data form the file's request.<br>
	 * Also do all the validation, messages can therefore be retrieved by the msgs objects.
	 * 
	 * @param request
	 * @param msgs The validation messages.
	 * @param teamActive team mode is active or not ?
	 * 
	 * @return List<String[]> object or null if there is an error.
	 * 
	 * @throws ImportException 
	 * @throws FatalException 
	 */
	public static List<String[]> getRawDataFromRequest(HttpServletRequest request,
			MatosoMessages msgs, boolean teamActive) throws ImportException, FatalException {
		
		FileItemIterator iter 	= null;
		FileItemStream item 	= null;
		InputStream stream 		= null;
		
		String importType 	= null;
		Integer rounds		= null;
		
		List<String[]> res = null;
		
		assert msgs != null;
		
		// Check that we have a file upload request
		if( ! ServletFileUpload.isMultipartContent(request) ) {
			throw new ImportException("The encryption type of the form is not multipart/form-data.", null);
		}
		
		boolean imported = false;
		
		try {
			
			// Parse the request
			iter = new ServletFileUpload().getItemIterator(request);
			
			while (iter.hasNext()) {
			    item = iter.next();
			    String name = item.getFieldName();
			    stream = item.openStream();
			    if (item.isFormField()) {
			    	
			    	String value =  Streams.asString(stream);
			    	
			        LOGGER.debug("Form field " + name + " with value "
			            + value + " detected.");
			        
			        if(name.equals("importType")) {
			        	importType = value;
			        } else if(name.equals(RequestCst.REQ_PARAM_ROUND)) {
			        	rounds = NumberUtils.getInteger(value);

			    		if (LOGGER.isDebugEnabled())
			    			LOGGER.debug("parameterRound =" + rounds);

			    		request.setAttribute(RequestCst.REQ_PARAM_ROUND, value);
			        }	
			        
			    } else {
			        LOGGER.debug("File field " + name + " with file name "
			            + item.getName() + " detected.");
			        
			        // this code portion can't be placed outside of the loop, quoted from the API :
			        //
			        // Note: There is an interaction between the iterator and its associated instances of FileItemStream: 
			        // By invoking Iterator.hasNext() on the iterator, you discard all data, which hasn't been read so far from the previous data.
			        //
			        // FileItemStream.ItemSkippedException
			        // This exception is thrown, if an attempt is made to read data from the InputStream, 
			        // which has been returned by openStream(), after Iterator.hasNext() has been invoked on the iterator, 
			        // which created the FileItemStream.
			        
			        if( ! StringUtils.isEmpty(importType) && ! imported ) {
			        	
			        	if(importType.equals("CSV") && name.equals("csvfile")) {
			    			
			        		if( !StringUtils.isEmpty(item.getName()) ) {
			        			res = ImportUtils.readCSV( stream );
			        			
			        			// file was there but no data
				    			if(res==null) res = new ArrayList<String[]>();
				    			
			    				// doing some validation
			    				validateData(res, msgs, null);
			        		}
			    			
			    			imported = true;
			    		
			    		} else if (importType.equals("XLS") && name.equals("xlsfile")) {
			    			
			    			if( !StringUtils.isEmpty(item.getName()) ) {
			    				XLSFile xls = ImportUtils.readXLS(stream, 0);
			    				
				    			// file was there but no data
				    			if(xls!=null && xls.getPlayersData()!=null) res = xls.getPlayersData();
				    			else res = new ArrayList<String[]>();
				    			
			    				// doing some validation
			    				validateData(res, msgs, "(xls-spreadsheet = " + xls.getPlayerSpreadsheetName() + ")");
			    			}
			    			
				    		imported = true;
			    		} 
			        	
			        }
			        
			    }
			}
			
		} catch (FileUploadException e) {
			throw new FatalException("Can't upload players file", e);
		} catch (IOException e) {
			throw new FatalException("Can't retrieve players file stream", e);
		} finally {
			if (stream != null) {
				try { stream.close(); } 
				catch (IOException e) { throw new FatalException("Can't close players file stream", e); }
			}
		}
		
		if( !imported ) {
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("player.mass.import.error.type.invalid"));
		}
		
		if(StringUtils.isEmpty(importType)) {
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("player.mass.import.error.type.missing"));
		}
		
		if(rounds == null || rounds <= 0) {
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("player.mass.import.error.nb.rounds.missing"));
		}
		
		if(res == null) {
			msgs.addMessage(MatosoMessage.ERROR, BundleCst.BUNDLE.getString("player.mass.import.error.no.file"));
		} else if(res.size() == 0) {
			msgs.addMessage(MatosoMessage.WARNING, BundleCst.BUNDLE.getString("player.mass.import.error.no.players"));
		} 
		
		// team validation
		if(teamActive)	TeamService.validate(res, msgs);
		
		return res;
	}

	/**
	 * Validate the players data coming from a file import.
	 * 
	 * @param data
	 * @param msgs To be filled with warnings / errors if problems occur.
	 * @param precisions Useful precision to be added in messages if warnings / errors.
	 */
	private static void validateData(List<String[]> data, MatosoMessages msgs,
			String precisions) {
		
		
		if(data==null || data.size()<=0) return;
		
		assert msgs != null;
		
		
		int nbRows = data.size();
		int maxNbCols = 0;
		for (int i = 0; i < nbRows; i++) {
			
			String[] da = data.get(i);
			if(da!=null && da.length > maxNbCols) maxNbCols = da.length;
		}
		
		
		
		if(maxNbCols < ApplicationCst.NB_COLUMNS_IMPORT_PLAYERS) {
			
			String formattedPrecisions =  StringUtils.isEmpty(precisions) ? "" : " " + precisions;
			
			msgs.addMessage(MatosoMessage.ERROR, I18nUtils.getMessage("player.mass.import.error.nb.cols.invalid", 
					Integer.toString(maxNbCols), 
					Integer.toString(ApplicationCst.NB_COLUMNS_IMPORT_PLAYERS), 
					formattedPrecisions));
		}
		
	}
	
}
