import sys
from os import listdir
import os.path
import os
import shutil
import statistics as st
import csv
version_iteration = [] 
all_test_cases = {}

def life_cycle_one_product(product_path):
    try:
        version_dirs = [version_path for version_path in listdir(product_path) if
                os.path.isdir(os.path.join(product_path,version_path))]
        version_dirs = sorted(version_dirs)
        version_iteration.extend(version_dirs)
        # print version_iteration
        version_dir_path = [os.path.join(product_path,version_path) for version_path in version_dirs]
    except Exception as E:
        print "the directory does not exist"
        exit(1)
    load_versions(version_dir_path,version_dirs)
    calculate_tc_life_cycle()
    version_cluster_list = life_cycle_clustered_by_version()
    version_cluster_statistics = life_cycle_statistic(version_cluster_list)
    output_csv(version_cluster_statistics,"result.csv")


def load_versions(versions_path,version_names):
    for ii in range(0,len(versions_path)):
        version_path = versions_path[ii]
        version_name = version_names[ii]
        load_one_version(version_path,version_name)

def load_one_version(version_path,version_name):
    test_cases= [test_case for test_case in listdir(version_path) if os.path.isfile(os.path.join(version_path,test_case))]
    test_cases_path = [os.path.join(version_path,test_case) for test_case in test_cases]
    for test_case_path in test_cases_path:
        test_case_file = open(test_case_path,'r')
        content = test_case_file.read()
        if not all_test_cases.has_key(content):
            all_test_cases[content] = []
        all_test_cases[content].append(version_name)

def calculate_tc_life_cycle():
    for test_case_key in all_test_cases.keys():
        test_case_result = []
        test_case_versions = all_test_cases[test_case_key]
        ii = 0
        for test_case_version in test_case_versions:
            test_case_result.append({"version": test_case_version, "life_cycle":
                version_iteration.index(test_case_version)-version_iteration.index(test_case_versions[0])})
            ii = ii + 1
        all_test_cases[test_case_key] = test_case_result

def life_cycle_clustered_by_version():
    result ={}
    for version in version_iteration:
        result[version] = []
    for test_case_key in all_test_cases.keys():
        one_version_cluster = {}
        test_case_info = all_test_cases[test_case_key]
        for tc_in_one_version in test_case_info:
            result[tc_in_one_version['version']].append(tc_in_one_version['life_cycle'])
    return result

def life_cycle_statistic(version_cluster_list):
    result = {}
    for version in version_iteration:
        result[version] = {}
    for version_cluster_key in version_cluster_list.keys():
        version_cluster_content = version_cluster_list[version_cluster_key]
        result[version_cluster_key]["version"] = version_cluster_key 
        result[version_cluster_key]["No of total tests"] = len(version_cluster_content)
        result[version_cluster_key]["No of new tests"] = version_cluster_content.count(0)
        #remove the test cases with age = 0
        content_without_zero = [value for value in version_cluster_content if value != 0]
        if content_without_zero != []:
            result[version_cluster_key]["Min"] = min(content_without_zero)
            result[version_cluster_key]["Max"] = max(content_without_zero)
            result[version_cluster_key]["Average"] = st.mean(content_without_zero)
            result[version_cluster_key]["Median"] = st.median(content_without_zero)
        else:
            result[version_cluster_key]["Min"] = 0
            result[version_cluster_key]["Max"] = 0
            result[version_cluster_key]["Average"] = 0
            result[version_cluster_key]["Median"] = 0
    return result

def output_csv(version_cluster_statistics,output_file):
    output_file_handle = open(output_file,'w')
    writer = csv.DictWriter(output_file_handle,fieldnames = ['version','No of total tests','No of new tests','Min','Max','Average','Median'])
    # print version_iteration
    writer.writeheader()
    print version_cluster_statistics.values()
    # for version_cluster in version_cluster_statistics:
        # writer.writerows(version_cluster)
    writer.writerows(version_cluster_statistics.values())


if __name__=="__main__":
    if len(sys.argv)!=2:
        print "usage: python folder_including_lda_input_for_one_product"
        exit(1)
    life_cycle_one_product(sys.argv[1])
