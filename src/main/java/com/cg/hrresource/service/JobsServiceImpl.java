package com.cg.hrresource.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hrresource.entities.Jobs;
import com.cg.hrresource.repository.JobsRepository;

@Service
public class JobsServiceImpl implements JobsService {

	@Autowired
	private JobsRepository jobsRepository;

	@Autowired
	public JobsServiceImpl(JobsRepository jobsRepository) {
		this.jobsRepository = jobsRepository;
	}

	@Override
	public Jobs addJob(Jobs jobs) {
		return jobsRepository.save(jobs);
	}

	@Override
	public List<Jobs> getAllJobs() {
		return jobsRepository.findAll();
	}

	@Override
	public Jobs modifyJob(Jobs jobs) {
		return jobsRepository.save(jobs);
	}

	@Override
	public Jobs createJob(Jobs job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs findBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
		return jobsRepository.findByMinSalaryAndMaxSalary(minSalary, maxSalary);
	}

//    @Override
//    public Jobs getJobById(String jobId) {
//        return jobsRepository.findById(jobId).orElse(null);
//    }

	public void saveJob(Jobs job) {
		jobsRepository.save(job);
	}

	public boolean updateSalary(String jobId, BigDecimal minSalary, BigDecimal maxSalary) {
		Optional<Jobs> optionalJob = jobsRepository.findById(jobId);

		if (optionalJob.isPresent()) {
			Jobs job = optionalJob.get();
			boolean isUpdated = false;

			if (minSalary != null) {
				job.setMinSalary(minSalary);
				isUpdated = true;
			}

			if (maxSalary != null) {
				job.setMaxSalary(maxSalary);
				isUpdated = true;
			}

			if (isUpdated) {
				jobsRepository.save(job);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean deleteJobById(String jobId) {
		java.util.Optional<Jobs> jobOptional = jobsRepository.findById(jobId);
		if (jobOptional.isPresent()) {
			jobsRepository.delete(jobOptional.get());
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteJobById1(String jobId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Jobs getJob() {
		// TODO Auto-generated method stub
		return null;
	}

//    @Override
//    public Object updateSalary(String jobId, BigDecimal minSalary, BigDecimal maxSalary) {
//        // TODO Auto-generated method stub
//        return null;
//    }

	@Override
	public Jobs findBySalary(BigDecimal minSalary, BigDecimal maxSalary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs getJobById(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs save(Jobs job) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs findById(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Jobs getJobById1(String jobId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//front end extra methods
	
	//front end extra methods
		@Override
	    public List<String> getJobIdsByJobTitle(String jobTitle) {
	        try {
	            List<Jobs> jobs = jobsRepository.findByJobTitle(jobTitle);
	            List<String> jobIds = new ArrayList<>();
	            for (Jobs job : jobs) {
	                jobIds.add(job.getJobId());
	            }
	            return jobIds;
	        } catch (Exception e) {
	            throw new RuntimeException("Error retrieving job IDs by job title", e);
	        }
	    }
		
		// FOR MIN AND MAX SALARY
		@Override
	    public BigDecimal getMinSalaryByJobTitle(String jobTitle) {
	        return jobsRepository.findMinSalaryByJobTitle(jobTitle);
	    }

	    @Override
	    public BigDecimal getMaxSalaryByJobTitle(String jobTitle) {
	        return jobsRepository.findMaxSalaryByJobTitle(jobTitle);
	    }

}