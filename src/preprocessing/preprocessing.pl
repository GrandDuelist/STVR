#!/usr/bin/perl -w

use lib "/home/zhihan/perl5/lib/perl5";
use HTML::Obliterate qw(extirpate_html);
use Spreadsheet::WriteExcel;
use Lingua::Stem;
#set the English stop words hash table
my %english_stop_words_table;

#set the special character hash table
my %special_characters_table;

#set the special words hash table
my %special_words_table;


#set the sample cases directory
my $dirname = "";

#set the command
my $my_command = "";

#step: some preparations
&build_stop_words_table();

&print_hint();

while ( $my_command ne "exit" ) {
	&wait_command();
	if ( $my_command eq "extract expected result" ) {

		&extract_information();

	}
	elsif ( $my_command eq "extract steps to perform" ) {

		&extract_information();

	}
	elsif ( $my_command eq "extract both" ) {

		&extract_information();

	}
	elsif ( $my_command eq "change to lower case" ) {

		&change_to_lower_case__from_files();

	}
	elsif ( $my_command eq "remove spec char" ) {

		&remove_spec_char_from_files();

	}
	elsif ( $my_command eq "remove stop words" ) {

		&remove_stop_words_from_files();

	}
	elsif ( $my_command eq "change work dir" ) {

		&change_work_dir();

	}
	elsif ( $my_command eq "exit" ) {

	}
	elsif ( $my_command eq "output to excel" ) {
		&output_file_to_excel();

	}elsif ( $my_command eq "stem words" ) {
		&stem_words_in_files();

	}elsif ( $my_command eq "remove url" ) {
		&remove_url_from_files();

	}elsif($my_command eq "remove empty files")
	{
		&remove_empty_file();
	}elsif($my_command eq "remove spec words")
	{
		&remove_special_words_from_files();
	}elsif($my_command eq "remove label"){
		&remove_label();
	}elsif($my_command eq "remove javascript")
	{
		&remove_javascript();
	}elsif($my_command eq "remove css style")
	{
		&remove_css_style();
	}elsif($my_command eq "auto surprise scissor")
	{
		&auto_surprise_scissor();
	}elsif($my_command eq "stvr input"){
		&auto_stvr_input();
	}elsif($my_command eq "stvr layerized"){
		&auto_stvr_layerized();
	}
	else {
		&wait_command();
	}
}

sub auto_stvr_input{
	my $top_dir = "../../data/LDA_INPUT_DATA/";
	opendir(DIR2,$top_dir) || die "Error  in opening dir $top_dir\n";
	my $sub_dir = "";
	while($sub_dir = readdir(DIR2)){
		if($sub_dir ne ".." and $sub_dir ne "."){
			my $sub_path = $top_dir.$sub_dir."\/";
			$dirname = $sub_path;	
			&auto_stvr();
			#&auto_surprise_scissor();
		}
		
	}
	closedir(DIR2);
}


sub auto_stvr_layerized(){
	my $top_dir = "../../data/LDA_LAYERIZED_DATA/";
	opendir(DIR2,$top_dir) || die "Error  in opening dir $top_dir\n";
	my $sub_dir = "";
	while($sub_dir = readdir(DIR2)){
		if($sub_dir ne ".." and $sub_dir ne "."){
			my $sub_path = $top_dir.$sub_dir."\/";
			opendir(DIR3,$sub_path) ||die "Error in opening dir $sub_path";
			while(my $sub_dir2 = readdir(DIR3)){
				if($sub_dir2 ne ".." and $sub_dir2 ne "."){
					my $sub_path2 = $sub_path.$sub_dir2."\/";
					$dirname = $sub_path2;
					&auto_stvr();
				}
			}
			closedir(DIR3);
		}
		
	}

	closedir(DIR2);
}
sub auto_stvr{
	#1. change to lower case
	#2. remove css style
	#3. remove javascript
	#4. remove label
	#5. remove url
	#6. remove spec char
	#7. stem word
	#8. remove empty files
	&change_to_lower_case__from_files();
	#&remove_css_style();
	#&remove_javascript();
	&remove_label();
	&remove_url_from_files();	
	&remove_special_words_from_files();
	&stem_words_in_files();
	&remove_empty_file();

}

sub change_work_dir {

	print("\ninput the new work directory:  ");
	$my_command = <STDIN>;
	print("\n $my_command");
}

sub remove_css_style {

print("end");
	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my $contents = <FH>;
					#remove javascript
					$contents =~ s/<style.*?\/style>//g;
					print($contents);
					
					
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					
					print OUT "$contents\n";
					
					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);
	print("end");
	
}
sub remove_javascript {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my $contents = <FH>;
					#remove javascript
					$contents =~ s/<javascript.*?\/javascript>//g;
					$contents =~ s/<script.+?\/script>//g;
					#print($contents);
					
					
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					
					print OUT "$contents\n";
					
					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}
#function    : extract in the directory
#parameter   : null
#input par   : null
#return      : null
#call methods: output_result_file
sub extract_information {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";
	my $number = 111;

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";
			print( $sub_dir_address. "\n" );

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {
				if ( $filename ne ".." and $filename ne "." ) {
if(!($filename =~ /.txt/)){
					#step4: get the file name
					my $whole_file_name =
					  $sub_dir_address . $filename;    # absolute file path

					#step5: processe the file
					my ( $a1, $a2 ) = &process_file($whole_file_name);

					#step:  remove files
					unlink($whole_file_name);

					#step6: output the file
					
						&output_result_file( $filename . ".txt",
						$sub_dir_address, $a1, $a2 );
					$number++;
				}
				}
			}

			closedir(SUB_DIR);
		}
	}
	closedir(DIR);
}

=head
function:  change the words to lower case of spectial directory
=cut

sub change_to_lower_case__from_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#change to lower case
							$per_line = lc($per_line);
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
							
								print OUT "$per_line ";
						
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}


=head
function:  change the words to lower case of spectial directory
=cut

sub remove_label {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#remove label
							$per_line = extirpate_html($per_line);
							
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
							
								print OUT "$per_line ";
						
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}


sub remove_empty_file {



	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					my $is_empty=0;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							$is_empty=1;
						}

					}
					close(FH);

					if($is_empty==0){
					unlink($whole_file_name);
					}

				
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}


sub stem_words_in_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#STEM words
							$per_line = &stemWords($per_line);
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
							
						print OUT "$per_line ";
						
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}

=head
function:  remove the special character of special directory
=cut

sub remove_spec_char_from_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					my $number;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#remove the special word
							( $number, $per_line ) =
							  &remove_special_characters( $per_line,
								\%special_characters_table);
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
							
						print OUT "$per_line ";
						
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}

=head
function:  remove the stop file of spectial directory
=cut

sub remove_stop_words_from_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					my $number;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#remove the enlish stop words
							( $number, $per_line ) =
							  &remove_stop_words( $per_line,
								\%english_stop_words_table );
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line ";
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}


=head
function:  remove the special words from files, selected by Dr. Hadi
=cut

sub remove_special_words_from_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					my $number;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#remove the enlish stop words
							( $number, $per_line ) =
							  &remove_special_words( $per_line,
								\%special_words_table );
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line ";
						}
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}


=head
function:  remove the stop file of spectial directory
=cut

sub remove_url_from_files {

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";
	while ( $sub_dirname = readdir(DIR) ) {
		if ( $sub_dirname ne ".." and $sub_dirname ne "." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dir_address\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;

					open( FH, $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );
					my @contents = <FH>;
					my $per_line;
					my $number;
					foreach $per_line (@contents) {

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							#remove the enlish stop words
							( $number, $per_line ) =
							  &remove_url( $per_line );
						}

					}
					close(FH);

					unlink($whole_file_name);

					open( OUT, ">>", $whole_file_name )
					  || ( die "can not open the file $whole_file_name\n" );

					foreach $per_line (@contents) {
						print OUT "$per_line ";
					}

					close(OUT);
				}

			}

			closedir(SUB_DIR);

		}
	}
	closedir(DIR);

}

#function    : show the explanation of input command
#parameter   : null
#input par   : null
#return      : null
#call methods: print
sub print_hint {
	print("\n");
	print("#the command explanation:\n");
	print(
		"#1: input \"change to lower case\" to change the words to lower case\n"
	);
	print("#2: input \"remove spec char\" to remove the spectial character\n");
	print(
"#3: input \"remove stop words\" to remove the english stop words in text\n"
	);
	print(
"#4: input \"change work dir\" to change the work directory of the preprocessing files`\n"
	);
	print("#5: input \"exit\" to exit the program\n");
	print(
"#6: input \"extract steps to perform\" to extract steps to perform from html\n"
	);
	print(
"#7: input \"extract expected result\" to extract expected result from html\n"
	);
	print(
"#8: input \"extract both\" to extrach both steps and result from html\n"
	);
	print(
"#9: input \"output to excel\" to extrach both steps and result from html\n"
	);
	print(
"#10:input \"stem words\" to stem words in the files\n"
	);
print(
"#11:input \"remove url\" to remove url in the files\n"
	);
print(
"#12:input \"remove empty files\" to remove empty files\n"
	);
print(
"#13:input \"remove spec words\" to remove special words from files\n"
	);
print("#14: input \"remove label\" to remove labels from files\n");
print("#15: input \"remove javascript\" to remove javascript \n");
print("#16: input \"stvr input\" to process all steps for stvr input  preprocessing\n");
print("#16: input \"stvr layrized\" to process all steps for stvr layerized  preprocessing\n");
print("#17: input \"remove css style\" to remove css from html\n");
}

#function    : process per file in the directory
#parameter   : $whole_file_name
#input par   : $whole_file_name,file name including directory path
#return      : null
#call methods: retrieve_useful_information
sub wait_command {
	print("\ninput your command:   ");
	$my_command = <STDIN>;
	chop($my_command);
}

#function    : process per file in the directory
#parameter   : $whole_file_name
#input par   : $whole_file_name,file name including directory path
#return      : $step_to_perform, $expected_result
#call methods: retrieve_useful_information
sub process_file {
	my ($whole_file_name) = @_;
	open( FH, $whole_file_name )
	  || die "can not open the file $whole_file_name\n";
	my @contents = <FH>;

	#retrieve steps to perform & expected results from html
	my ( $a1, $a2 ) = &retrieve_useful_information(@contents);
	close FH;
	return ( $a1, $a2 );

}

#function    : get useful information, between body
#parameter   : $orig_contents
#input par   : $orig_contents, the original html text
#return      : @orig_steps_to_perform, the test step; @orig_expected_result
#call methods: null
sub retrieve_text_between_body {
	my (@orig_contents)       = @_;
	my @orig_steps_to_perform = ();    #initial array
	my @orig_expected_result  = ();

	my $i         = 1;
	my $is_useful = 0;                 #not useful when 0
	foreach $per_line (@orig_contents) {

		chomp($per_line);

		#at first, we get steps to perform
		if ( $is_useful == 0 && $per_line =~ /Steps to Perform/ ) {
			$is_useful = 1;            #set is useful, begin to record
			$per_line =
			  substr( $per_line, index( $per_line, "Steps to Perform" ), );
		}

		if ( $is_useful == 1 ) {
			push( @orig_steps_to_perform, $per_line );

		}

		if ( $is_useful == 1 && $per_line =~ /<\/html>/ ) {
			$is_useful = 2;            #set is useful, begin to record
		}

		#then , we get expeced result
		if ( $is_useful == 2 && $per_line =~ /Expected Results/ ) {
			$is_useful = 3;            #set is useful, begin to record
			$per_line =
			  substr( $per_line, index( $per_line, "Expected Results" ), );

		}

		if ( $is_useful == 3 ) {
			push( @orig_expected_result, $per_line );

		}

		if ( $is_useful == 3 && $per_line =~ /<\/html>/ ) {
			$is_useful = 4;            #set is useful, begin to record
		}

		$i++;
	}

	return ( \@orig_steps_to_perform, \@orig_expected_result );

}

#function    : get useful information, no label
#parameter   : $orig_contents
#input par   : $orig_contents, the original html text
#return      : @steps_to_perform, the test step; @expected_result
#call methods: retrieve_useful_information
sub retrieve_useful_information {
	my (@orig_contents) = @_;

	my ( $a1, $a2 ) = &retrieve_text_between_body(@orig_contents);

	my @steps_to_perform = ();
	my @expected_result  = ();

	foreach $per_line ( @{$a1} ) {

		#remove label
		$per_line = extirpate_html($per_line);

		#change all words to lower case
		#	$per_line = lc($per_line);

		#	if($per_line and !($per_line=~/^\s+$/)){

#remove special characters
#		(my $number_removed,$per_line)=&remove_special_characters($per_line,\%special_characters_table);

#remove the enlish stop words
#		(my $number,$per_line)=&remove_stop_words($per_line,\%english_stop_words_table);
		push( @steps_to_perform, $per_line );

		#	}

	}

	foreach $per_line ( @{$a2} ) {

		#remove label
		$per_line = extirpate_html($per_line);
		if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

#remove the english stop words in expected result
#	(my $number,$per_line)=&remove_stop_words($per_line,\%english_stop_words_table);
			push( @expected_result, $per_line );
		}

	}
	return ( \@steps_to_perform, \@expected_result );

}

#function    : output the result to a  file
#parameter   : $output_file, $output_dir, @a1, @a2
#input par   : $output_file, the file name of output file;
#              $output_dir, the directory paht of output files
#              $@a1 @a2, output contents array.
#return      : success 1;   failed 0
#call methods: null
sub output_result_file {

	my $is_success = 0;
	my ( $output_file, $output_dir, $a1, $a2 ) = @_;
	my @steps_to_perform = @{$a1};
	my @expected_result  = @{$a2};

	my $whole_path = $output_dir . $output_file;
	open( OUT, ">>", $whole_path ) || die "can not open the file $whole_path\n";
	my $per_line;

	if (  $my_command eq "extract both" )
	{
		foreach $per_line (@steps_to_perform) {
			if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line\n";
						}
		}
	}
	
	
	 if($my_command eq "extract steps to perform")
	 {
	 	foreach $per_line (@steps_to_perform) {
	 		if ( !($per_line =~ /Steps to Perform/) ) {
								
			if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line\n";
						}
		}
	 	}
	 }
	 
	

	if (   $my_command eq "extract both" )
	{
		foreach $per_line (@expected_result) {
			if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line\n";
						}
		}
	}
	
	
	
	 if($my_command eq "extract expected result")
	 {
	 	foreach $per_line (@expected_result) {
	 		if ( !($per_line =~ /Expected Results/) ) {
								
			if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {
						print OUT "$per_line\n";
						}
	 		}
		}
	 }
	 
	 

	close OUT;
	$is_success = 1;
	return $is_success;

}

#function    : build the stop words table, which show in the test suite
#parameter   : null
#input par   : null
#return      : null
#call methods: null
sub build_stop_words_table {

	my @english = qw(
	  a able about above according accordingly across actually after afterwards
	  again against all allow allows almost alone along already also although
	  always am among amongst an and another any anybody anyhow anyone anything anyway
	  anyways anywhere apart appear appreciate appropriate are around as aside
	  ask asking associated at available away awfully b be became because become
	  becomes becoming been before beforehand behind being believe below beside besides best
	  better between beyond both brief but by c came can cannot cant cause causes certain
	  certainly changes clearly co com come comes concerning consequently consider considering
	  contain containing contains corresponding could course currently
	  d definitely described despite did different do does doing done down downwards
	  during e each edu eg eight either else elsewhere enough entirely especially et etc even ever
	  every everybody everyone everything everywhere ex exactly example except f far few
	  fifth first five followed following follows for former formerly forth four from
	  further furthermore g get gets getting given li ul js
	  gives go goes going gone got gotten greetings h had happens hardly has have
	  having he hello help hence her here hereafter hereby herein hereupon hers herself hi him himself
	  his hither hopefully how howbeit however i ie if
	  ignored immediate in inasmuch inc indeed indicate indicated indicates inner
	  insofar instead into inward is it its itself j just k keep keeps kept know knows known l
	  last lately later latter latterly least less lest let like liked likely little
	  look looking looks ltd m mainly many may maybe me mean meanwhile merely
	  might more moreover most mostly much must my myself n name namely nd
	  near nearly necessary need needs neither never nevertheless new next nine no
	  nobody non none noone nor normally not nothing novel now nowhere o obviously of
	  off often oh ok okay old on once one ones only onto or
	  other others otherwise ought our ours ourselves out outside over overall own p
	  particular particularly per perhaps placed please plus possible presumably probably provides q que
	  quite qv r rather rd re really reasonably regarding regardless regards
	  relatively respectively right s said same saw say saying says second secondly see seeing seem
	  seemed seeming seems seen self selves sensible sent serious seriously seven
	  several shall she should since six so some somebody somehow someone something sometime
	  sometimes somewhat somewhere soon sorry specified specify specifying still sub such sup sure t take
	  taken tell tends th than thank thanks thanx that thats the their theirs
	  them themselves then thence there thereafter thereby therefore therein theres
	  thereupon these they think third this thorough thoroughly those though three through throughout thru
	  thus to together too took toward towards tried tries truly try trying twice two u
	  un under unfortunately unless unlikely until unto up upon us use used useful
	  uses using usually uucp v value various very via viz vs w want
	  wants was way we welcome well went were what whatever when whence whenever
	  where whereafter whereas whereby wherein whereupon wherever whether which while
	  whither who whoever whole whom whose why will willing wish with within without wonder would would
	  x y yes yet you your yours yourself yourselves z zero);

	my @spec_char = qw(. , - + = _ ; : " ' / ( ) [ ] | ? ! & \\);
	
	foreach my $word (@english) {
		$english_stop_words_table{$word} = 1;
	}
	
	my @spec_words=qw(open click page mozilla firefox org select press web error
		 tab check step repeat webiste google back test remove box ensur remov choos
		button choose on ensure start);
		
	foreach my $word(@spec_words)
	{
		$special_words_table{$word}=1;
	}

	foreach my $word (@spec_char) {
		$special_characters_table{$word} = 1;
	}

}

#function    : remove english stop words
#parameter   : $str_in => string, %ref_stop_words => hash
#input par   : $str_in, the string processing; $ref_stops_words, reference stops words table
#return      : null
#call methods: null
#prerequites : method build_stop_words_table executed
#sub remove_stop_words
#{
#	my ($str_in,$ref_stop_words)=@_;
#	my $str_out = $str_in;
#	my $number_removed=0;
#
#	foreach my $word(@{$ref_stop_words})
#	{
#		$str_out=~s/$word//gs;
#	}
#
#	return $str_out;
#}

sub remove_stop_words {
	my $wordsIn = shift;
	my $stops   = shift;

	my $wordsOut   = "";
	my $numRemoved = 0;

	# Make sure to lowercase the wordsIn, because the stopwords are themselves
	# lowercase.
	for my $w ( split /\s+/, $wordsIn ) {
		if ( exists( $stops->{ lc($w) } ) ) { ++$numRemoved }
		else {
			$wordsOut = "$wordsOut $w";
		}
	}
	return ( $numRemoved, removeDuplicateSpaces($wordsOut) );
}

=title remove_special_characters
 function   :  remove special characters and synboms from string
 parameter  :  $str_in => string; %ref_spec_char => hash array;
 input par  :  $str_in, string to process; $ref_spec_char, address of ref_spec_char
 return par :  $number_removed, removed char number; $str_out, string after processing
prerequites : method build_stop_words_table executed
=cut

sub remove_special_characters {
	my ( $str_in, $ref_spec_char ) = @_;

	my $str_out        = "";
	my $number_removed = 0;

	for my $w ( split /(\s|,|\.|\||-|\/|\(|\)|'|"|\[|\]|\?|!|:|\\|\{|\}|;|‘|’)+/, $str_in )
	{
		if ( exists( $ref_spec_char->{$w} ) ) { ++$number_removed }
		else {
			$str_out = "$str_out $w";
		}
	}
	return ( $number_removed, removeDuplicateSpaces($str_out) );
}

=head2 removeDuplicateSpaces
 Title    : removeDuplicateSpaces
 Usage    : removeDuplicateSpaces($wordsIn)
 Function : A helper function to remove duplicate whitespace in $wordsIn
          : Leaves words alone.
 Returns  : $wordsOut => string, the remaining words
 Args     : named arguments:
          : $inWords => string, the white-space delimited words to process
=cut

sub removeDuplicateSpaces {
	my $inWords = shift;
	$inWords =~ s/\r/ /g;
	$inWords =~ s/\n/ /g;
	$inWords =~ s/\t/ /g;
	$inWords =~ s/  +/ /g;
	$inWords =~ s/^ +//g;
	return $inWords;
}

=head2
 Title    : output to the excel file
 Usage    : output_file_to_excel()
 Function : A helper function to remove duplicate whitespace in $wordsIn
          : Leaves words alone.
 Returns  : null
 Args     : named arguments:
          : null
=cut

sub output_file_to_excel {

	# create a new Excel workbook
	my $workbook =
	  Spreadsheet::WriteExcel->new( $dirname . '../excel/limus.xls' );
	my $worksheet = $workbook->add_worksheet("limus");

	#add format header_format
	my $header_format = $workbook->add_format();
	$header_format->set_size('12');
	$header_format->set_align('center');
	$header_format->set_bold();

	#add format content_format
	my $content_format = $workbook->add_format();
	$content_format->set_size('9');
	$content_format->set_align('center');
	$content_format->set_text_wrap();
	#$content_format->set_bg_color('22');

	#set the width of column
	$worksheet->set_column( "A:A", 10 );
	$worksheet->set_column( "B:B", 30 );
	$worksheet->set_column( "C:C", 100 );
	$worksheet->set_column( "D:D", 100 );

	#write header
	$worksheet->write( "A1", "version",          $header_format );
	$worksheet->write( "B1", "file name",        $header_format );
	$worksheet->write( "C1", "steps to perform", $header_format );
	$worksheet->write( "D1", "expected result",  $header_format );

	#step1: open the directory which contains test suites
	opendir( DIR, $dirname ) || die "Error in opening dir $dirname\n";

	my $sub_dirname = "";

	#first line
	my $line = 2;
	while ( $sub_dirname = readdir(DIR) ) {

		if ( $sub_dirname ne "." and $sub_dirname ne ".." ) {

			my $sub_dir_address = $dirname . $sub_dirname . "\/";

			opendir( SUB_DIR, $sub_dir_address )
			  || die "Error in opening dir $sub_dirname\n";

			#step2: get per file by while function
			while ( ( $filename = readdir(SUB_DIR) ) ) {
				my $steps_to_perform = "";
				my $expected_results = "";

				if ( $filename ne ".." and $filename ne "." ) {
					my $whole_file_name = $sub_dir_address . $filename;
					print("$whole_file_name\n");
					open( FH, $whole_file_name )
					  || die "can not open the file $whole_file_name\n";

					my @contents = <FH>;
					my $per_line;
					my $number;

					$number = 2;
					my $control = 1;
					
					#set version
					$worksheet->write( "A" . $line,
					$sub_dirname, $content_format );

					#set filename
					$worksheet->write( "B" . $line,
							$filename, $content_format );
							
					foreach $per_line (@contents) {

						chop($per_line);

					

						if ( $per_line and !( $per_line =~ /^\s+$/ ) ) {

							if ( $per_line =~ /steps perform/ ) {

								$control = 2;

							}
							elsif ( $per_line =~ /expected results/ ) {

								$control = 3;

							}

							if ( $control == 2 ) {

								#set steps to perform
								if ( $per_line =~ /steps perform/ ) {
								}
								else {
									$steps_to_perform =
									  $steps_to_perform . $per_line . ";\n";
								}

								#$worksheet->write( "C" . $line,
								#$per_line.";", $content_format );

							}
							elsif ( $control == 3 ) {

								#set expected results
								if ( $per_line =~ /expected results/ ) {
									
								}else{
									
									$expected_results =
									  $expected_results . $per_line . ";\n";
								}

								#$worksheet->write( "D" . $line,
								#$per_line.";", $content_format );
							}

						}

					}
					close(FH);
					$worksheet->write( "C" . $line,
						$steps_to_perform, $content_format );

					$worksheet->write( "D" . $line,
						$expected_results, $content_format );

					#line number
					$line++;
				}
			}

			close(SUB_DIR);

		}
		close(DIR);
	}
}



=head2 stemWords
Title    : stemWords
Usage    : stemWords($wordsIn)
Function : Stems all the words in $wordsIn
Returns  : $wordsOut => string, the stemmed words
Args     : named arguments:
         : $wordsIn => string, the white-space delimited words to process
=cut
sub stemWords{
    my $wordsIn  = shift;
    my $wordsOut = "";

    for my $w (split /\s+/, $wordsIn) {
  	      $wordsOut = "$wordsOut ".stem($w);
     }
 
     return removeDuplicateSpaces($wordsOut);
}



=head2 stem
Title    : stem
Usage    : stem($word)
Function : Applies the Porter stemming algorithm to $word
Returns  : $word => string, the stemmed word
Args     : named arguments:
         : $word => string, the word to stem
=cut
sub stem {
    my ($word) = @_;
    my $stemref = Lingua::Stem::stem( $word );
    return $stemref->[0];
}


=head remove url
=cut
sub remove_url {
	my $wordsIn = shift;
	

	my $wordsOut   = "";
	my $numRemoved = 0;

	# Make sure to lowercase the wordsIn, because the stopwords are themselves
	# lowercase.
	for my $w ( split /\s+/, $wordsIn ) {
		if ( $w =~ /http:|https:|HTTP:|HTTPS:|www./)  { ++$numRemoved }
		else {
			$wordsOut = "$wordsOut $w";
		}
	}
	return ( $numRemoved, removeDuplicateSpaces($wordsOut) );
}


=title remove_special_word
 function   :  remove special words from string, the words are selected by human
 parameter  :  $str_in => string; %ref_spec_char => hash array;
 input par  :  $str_in, string to process; $ref_spec_char, address of ref_spec_char
 return par :  $number_removed, removed char number; $str_out, string after processing
prerequites : method build_stop_words_table executed
=cut

sub remove_special_words {
	my ( $str_in, $ref_spec_words ) = @_;

	my $str_out        = "";
	my $number_removed = 0;

	for my $w ( split /(\s|,|\.|\||-|\/|\(|\)|'|"|\[|\]|\?|!|:|\\)+/, $str_in )
	{
		if ( exists( $ref_spec_words->{$w} ) ) { ++$number_removed }
		else {
			$str_out = "$str_out $w";
		}
	}
	return ( $number_removed, removeDuplicateSpaces($str_out) );
}

=title remove duplicated words
=cut

sub remove_dup_words{
	
}
